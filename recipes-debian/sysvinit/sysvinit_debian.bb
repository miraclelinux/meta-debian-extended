SUMMARY = "System-V like init"
DESCRIPTION = "This package is required to boot in most configurations.  It provides the /sbin/init program.  This is the first process started on boot, and the last process terminated before the system halts."
HOMEPAGE = "http://savannah.nongnu.org/projects/sysvinit/"
SECTION = "base"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=393d657c17880c7708d7fd7f09b21b85 \
                    file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r0"

RDEPENDS_${PN} = "${PN}-inittab"

inherit update-alternatives distro_features_check debian-package
require recipes-debian/sources/sysvinit.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-core/sysvinit/sysvinit:"
SRC_URI += "file://install.patch \
           file://crypt-lib.patch \
           file://rcS-default \
           file://rc \
           file://rcS \
           file://bootlogd.init \
           file://01_bootlogd"

DEPENDS_append = " update-rc.d-native base-passwd virtual/crypt"

REQUIRED_DISTRO_FEATURES = "sysvinit"

ALTERNATIVE_${PN} = "init mountpoint halt reboot runlevel shutdown poweroff last lastb mesg utmpdump wall"

ALTERNATIVE_PRIORITY = "200"

ALTERNATIVE_LINK_NAME[init] = "${base_sbindir}/init"
ALTERNATIVE_PRIORITY[init] = "50"

ALTERNATIVE_LINK_NAME[halt] = "${base_sbindir}/halt"
ALTERNATIVE_LINK_NAME[reboot] = "${base_sbindir}/reboot"
ALTERNATIVE_LINK_NAME[runlevel] = "${base_sbindir}/runlevel"
ALTERNATIVE_LINK_NAME[shutdown] = "${base_sbindir}/shutdown"
ALTERNATIVE_LINK_NAME[poweroff] = "${base_sbindir}/poweroff"

ALTERNATIVE_${PN}-pidof = "pidof"
ALTERNATIVE_LINK_NAME[pidof] = "${base_bindir}/pidof"

ALTERNATIVE_${PN}-sulogin = "sulogin"
ALTERNATIVE_LINK_NAME[sulogin] = "${base_sbindir}/sulogin"

PACKAGES =+ "sysvinit-pidof sysvinit-sulogin"
FILES_${PN} += "${base_sbindir}/* ${base_bindir}/*"
FILES_sysvinit-pidof = "${base_bindir}/pidof.sysvinit ${base_sbindir}/killall5"
FILES_sysvinit-sulogin = "${base_sbindir}/sulogin.sysvinit"

RDEPENDS_${PN} += "sysvinit-pidof initd-functions"


export LCRYPT = "-lcrypt"

EXTRA_OEMAKE += "'base_bindir=${base_bindir}' \
        'base_sbindir=${base_sbindir}' \
        'bindir=${bindir}' \
        'sbindir=${sbindir}' \
        'sysconfdir=${sysconfdir}' \
        'includedir=${includedir}' \
        'mandir=${mandir}'"

do_install () {
        oe_runmake 'ROOT=${D}' install

        install -d ${D}${sysconfdir} \
                   ${D}${sysconfdir}/default \
                   ${D}${sysconfdir}/init.d
        for level in S 0 1 2 3 4 5 6; do
                install -d ${D}${sysconfdir}/rc$level.d
        done

       install -m 0644    ${WORKDIR}/rcS-default       ${D}${sysconfdir}/default/rcS
       install -m 0755    ${WORKDIR}/rc                ${D}${sysconfdir}/init.d
       install -m 0755    ${WORKDIR}/rcS               ${D}${sysconfdir}/init.d
       install -m 0755    ${WORKDIR}/bootlogd.init     ${D}${sysconfdir}/init.d/bootlogd
       ln -sf bootlogd ${D}${sysconfdir}/init.d/stop-bootlogd

       update-rc.d -r ${D} bootlogd start 07 S .
       update-rc.d -r ${D} stop-bootlogd start 99 2 3 4 5 .

       install -d ${D}${sysconfdir}/default/volatiles
       install -m 0644 ${WORKDIR}/01_bootlogd ${D}${sysconfdir}/default/volatiles

       chown root:shutdown ${D}${base_sbindir}/halt ${D}${base_sbindir}/shutdown
       chmod o-x,u+s ${D}${base_sbindir}/halt ${D}${base_sbindir}/shutdown
}
