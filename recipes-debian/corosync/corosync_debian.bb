#base recipe: meta-networking/recipes-extended/corosync/corosync_3.0.3.bb
#base branch: dunfell
#base commit: 6792ebdd966aa0fb662989529193a0940fbfee00

SUMMARY = "The Corosync Cluster Engine and Application Programming Interfaces"
DESCRIPTION = "This package contains the Corosync Cluster Engine Executive, several default \
APIs and libraries, default configuration files, and an init script."
HOMEPAGE = "http://corosync.github.io/corosync/"

SECTION = "base"

inherit autotools pkgconfig systemd useradd debian-package
require recipes-debian/sources/corosync.inc

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a85eb4ce24033adb6088dd1d6ffc5e5d"

DEPENDS = "groff-native nss libqb kronosnet"

SRC_URI += "file://corosync"

SYSTEMD_SERVICE_${PN} = "corosync.service corosync-notifyd.service \
                         ${@bb.utils.contains('PACKAGECONFIG', 'qdevice', 'corosync-qdevice.service', '', d)} \
                         ${@bb.utils.contains('PACKAGECONFIG', 'qnetd', 'corosync-qnetd.service', '', d)} \
"
SYSTEMD_AUTO_ENABLE = "disable"

INITSCRIPT_NAME = "corosync-daemon"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} \
                   dbus snmp \
"

PACKAGECONFIG[dbus] = "--enable-dbus,--disable-dbus,dbus"
PACKAGECONFIG[snmp] = "--enable-snmp,--disable-snmp,net-snmp"
PACKAGECONFIG[systemd] = "--enable-systemd --with-systemddir=${systemd_system_unitdir},--disable-systemd --without-systemddir,systemd"

EXTRA_OECONF = "ac_cv_path_BASHPATH=${base_bindir}/bash ap_cv_cc_pie=no --enable-xmlconf"
EXTRA_OEMAKE = "tmpfilesdir_DATA="

do_install_append() {
    install -D -m 0644 ${S}/conf/corosync.conf.example ${D}/${sysconfdir}/corosync/corosync.conf
    install -d ${D}${sysconfdir}/sysconfig/
    install -m 0644 ${S}/init/corosync.sysconfig.example ${D}${sysconfdir}/sysconfig/corosync
    install -m 0644 ${S}/tools/corosync-notifyd.sysconfig.example ${D}${sysconfdir}/sysconfig/corosync-notifyd

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${S}/debian/corosync.default ${D}${sysconfdir}/default/corosync

    install -d ${D}${datadir}/augeas/lenses/tests
    install -m 0644 ${S}/conf/lenses/corosync.aug ${D}${datadir}/augeas/lenses
    install -m 0644 ${S}/conf/lenses/tests/test_corosync.aug ${D}${datadir}/augeas/lenses/tests

    rm -rf "${D}${localstatedir}/run"

    install -d ${D}${sysconfdir}/default/volatiles
    echo "d root root 0755 ${localstatedir}/log/corosync none" > ${D}${sysconfdir}/default/volatiles/05_corosync

    if [ ${@bb.utils.filter('PACKAGECONFIG', 'qnetd', d)} ]; then
        chown -R coroqnetd:coroqnetd ${D}${sysconfdir}/${BPN}/qnetd
        echo "d coroqnetd coroqnetd 0770 /var/run/corosync-qnetd none" >> ${D}${sysconfdir}/default/volatiles/05_corosync
    fi

    if [ ${@bb.utils.filter('DISTRO_FEATURES','systemd',d)} ]; then
        install -d ${D}${sysconfdir}/tmpfiles.d
        echo "d ${localstatedir}/log/corosync - - - -" > ${D}${sysconfdir}/tmpfiles.d/corosync.conf
    fi

    sed -i 's/cluster/corosync/' ${D}${sysconfdir}/logrotate.d/corosync
    if [ ${@bb.utils.filter('DISTRO_FEATURES','systemd',d)} ]; then
        sed -i 's/sysconfig/default/' ${D}${systemd_system_unitdir}/corosync.service
    fi
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/corosync ${D}${sysconfdir}/init.d
}

RDEPENDS_${PN} += "bash lsb libxslt-bin libqb ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'sysvinit-pidof', 'procps', d)}"

FILES_${PN} += "${datadir}/augeas/*"
FILES_${PN}-dbg += "${libexecdir}/lcrso/.debug"
FILES_${PN}-doc += "${datadir}/snmp/mibs/COROSYNC-MIB.txt"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system coroqnetd"
USERADD_PARAM_${PN} = "--system -d / -M -s /bin/nologin -c 'User for corosync-qnetd' -g coroqnetd coroqnetd"
