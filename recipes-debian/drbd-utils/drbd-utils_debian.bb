#base recipe: meta-openembedded/meta-networking/recipes-support/drbd/drbd-utils_9.5.0.bb
#base branch: thud
#base commit: 446bd615fd7cb9bc7a159fe5c2019ed08d1a7a93

SUMMARY = "Distributed block device driver for Linux"
DESCRIPTION = "DRBD mirrors a block device over the network to another machine.\
DRBD mirrors a block device over the network to another machine.\
Think of it as networked raid 1. It is a building block for\
setting up high availability (HA) clusters."
HOMEPAGE = "http://www.drbd.org/"
SECTION = "admin"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=5574c6965ae5f583e55880e397fbb018"

UPSTREAM_CHECK_URI = "https://github.com/LINBIT/drbd-utils/releases"

SYSTEMD_SERVICE_${PN} = "drbd.service"
SYSTEMD_AUTO_ENABLE = "disable"

inherit autotools-brokensep systemd debian-package
require recipes-debian/sources/drbd-utils.inc

SRC_URI += "file://0001-drbd-drbd-tools-only-rmmod-if-DRBD-is-a-module.patch \
            file://0002-netlink-prepare-for-kernel-v5.2.patch \
            file://0003-netlink-Add-NLA_F_NESTED-flag-to-nested-attribute.patch \
            file://configure.ac.patch \
            file://drbd.conf \
           "

EXTRA_OECONF = " \
                --with-initdir=/etc/init.d    \
                --without-pacemaker           \
                --without-rgmanager           \
                --without-bashcompletion      \
                --with-distro debian          \
                --with-initscripttype=both    \
                --with-systemdunitdir=${systemd_unitdir}/system \
                --without-manual \
               "

do_configure_prepend() {
    # move the the file under folder /lib/drbd/ to /usr/lib/drbd when usrmerge enabled
    if ${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', 'true', 'false', d)}; then
        for m_file in `find ${S} -name 'Makefile.in'`; do
            sed -i -e "s;\$(DESTDIR)\/lib\/drbd;\$(DESTDIR)\${nonarch_libdir}\/drbd;g" $m_file
        done
        # move the the file under folder /lib/udev/ to /usr/lib/udev when usrmerge enabled
        sed -i -e "s;default_udevdir=/lib/udev;default_udevdir=\${prefix}/lib/udev;g" ${S}/configure.ac
    fi

}

do_install_append() {
    install -d ${D}${base_libdir}/modules-load.d
    install -m 644 ${WORKDIR}/drbd.conf ${D}${base_libdir}/modules-load.d

    install -d ${D}${libdir}/drbd
    install -m 755 ${S}/scripts/crm-fence-peer.sh ${D}${libdir}/drbd
    install -m 755 ${S}/scripts/crm-fence-peer.9.sh ${D}${libdir}/drbd
    ln -sf crm-fence-peer.sh ${D}${libdir}/drbd/crm-unfence-peer.sh
    ln -sf crm-fence-peer.9.sh ${D}${libdir}/drbd/crm-unfence-peer.9.sh
    install -m 755 ${S}/scripts/rhcs_fence ${D}${libdir}/drbd

    install -d ${D}${libdir}/ocf/resource.d/linbit
    install -m 755 ${S}/scripts/drbd.ocf ${D}${libdir}/ocf/resource.d/linbit/drbd
    install -m 755 ${S}/scripts/drbd.shellfuncs.sh ${D}${libdir}/ocf/resource.d/linbit/drbd.shellfuncs.sh

    install -d ${D}${datadir}/bash-completion/completions
    install -m 755 ${S}/scripts/drbdadm.bash_completion ${D}${datadir}/bash-completion/completions/drbdadm

    install -d ${D}${datadir}/cluster
    install -m 644 ${S}/scripts/drbd.metadata.rhcs ${D}${datadir}/cluster/drbd.metadata
    install -m 755 ${S}/scripts/drbd.sh.rhcs ${D}${datadir}/cluster/drbd.sh

    install -d ${D}${docdir}/drbd-utils
    install -m 644 ${S}/debian/NEWS ${D}${docdir}/drbd-utils/NEWS.Debian
    install -m 644 ${S}/debian/README.Debian ${D}${docdir}/drbd-utils/README.Debian
    install -m 644 ${S}/debian/changelog ${D}${docdir}/drbd-utils/changelog.Debian
    install -m 644 ${S}/ChangeLog ${D}${docdir}/drbd-utils/changelog
    install -m 644 ${S}/debian/copyright ${D}${docdir}/drbd-utils/copyright
    install -d ${D}${docdir}/drbd-utils/examples
    install -m 644 ${S}/scripts/drbd.conf.example ${D}${docdir}/drbd-utils/examples/drbd.conf.example

    install -d ${D}${mandir}/ja/man5
    install -m 644 ${S}/documentation/ja/v84/drbd.conf.5 ${D}${mandir}/ja/man5/drbd.conf-8.4.5
    install -m 644 ${S}/documentation/ja/v9/drbd.conf.5 ${D}${mandir}/ja/man5/drbd.conf-9.0.5
    ln -sf drbd.conf-8.4.5 ${D}${mandir}/ja/man5/drbd.conf.5

    install -d ${D}${mandir}/ja/man8
    install -m 644 ${S}/documentation/ja/v84/drbd.8 ${D}${mandir}/ja/man8/drbd-8.4.8
    install -m 644 ${S}/documentation/ja/v9/drbd.8 ${D}${mandir}/ja/man8/drbd-9.0.8
    install -m 644 ${S}/documentation/ja/v9/drbd-overview.8 ${D}${mandir}/ja/man8/drbd-overview-9.0.8
    ln -sf drbd-overview-9.0.8 ${D}${mandir}/ja/man8/drbd-overview.8
    ln -sf drbd-8.4.8 ${D}${mandir}/ja/man8/drbd.8

    install -m 644 ${S}/documentation/ja/v84/drbdadm.8 ${D}${mandir}/ja/man8/drbdadm-8.4.8
    install -m 644 ${S}/documentation/ja/v9/drbdadm.8 ${D}${mandir}/ja/man8/drbdadm-9.0.8
    ln -sf drbdadm-8.4.8 ${D}${mandir}/ja/man8/drbdadm.8

    install -m 644 ${S}/documentation/ja/v84/drbddisk.8 ${D}${mandir}/ja/man8/drbddisk-8.4.8
    ln -sf drbddisk-8.4.8 ${D}${mandir}/ja/man8/drbddisk.8

    install -m 644 ${S}/documentation/ja/v84/drbdmeta.8 ${D}${mandir}/ja/man8/drbdmeta-8.4.8
    install -m 644 ${S}/documentation/ja/v9/drbdmeta.8 ${D}${mandir}/ja/man8/drbdmeta-9.0.8
    ln -sf drbdmeta-8.4.8 ${D}${mandir}/ja/man8/drbdmeta.8

    install -m 644 ${S}/documentation/ja/v9/drbdmon.8 ${D}${mandir}/ja/man8/drbdmon-9.0.8
    ln -sf drbdmon-9.0.8 ${D}${mandir}/ja/man8/drbdmon.8

    install -m 644 ${S}/documentation/ja/v84/drbdsetup.8 ${D}${mandir}/ja/man8/drbdsetup-8.4.8
    install -m 644 ${S}/documentation/ja/v9/drbdsetup.8 ${D}${mandir}/ja/man8/drbdsetup-9.0.8
    ln -sf drbdsetup-8.4.8 ${D}${mandir}/ja/man8/drbdsetup.8

    install -d ${D}${mandir}/man5
    install -m 644 ${S}/documentation/v83/drbd.conf.5 ${D}${mandir}/man5/drbd.conf-8.3.5
    install -m 644 ${S}/documentation/v84/drbd.conf.5 ${D}${mandir}/man5/drbd.conf-8.4.5
    install -m 644 ${S}/documentation/v9/drbd.conf.5 ${D}${mandir}/man5/drbd.conf-9.0.5
    ln -sf drbd.conf-8.4.5 ${D}${mandir}/man5/drbd.conf.5

    install -d ${D}${mandir}/man8
    install -m 644 ${S}/documentation/v83/drbd.8 ${D}${mandir}/man8/drbd-8.3.8
    install -m 644 ${S}/documentation/v84/drbd.8 ${D}${mandir}/man8/drbd-8.4.8
    install -m 644 ${S}/documentation/v9/drbd.8 ${D}${mandir}/man8/drbd-9.0.8
    install -m 644 ${S}/documentation/v9/drbd-overview.8 ${D}${mandir}/man8/drbd-overview-9.0.8
    ln -sf drbd-overview-9.0.8 ${D}${mandir}/man8/drbd-overview.8
    ln -sf drbd-8.4.8 ${D}${mandir}/man8/drbd.8

    install -m 644 ${S}/documentation/v83/drbdadm.8 ${D}${mandir}/man8/drbdadm-8.3.8
    install -m 644 ${S}/documentation/v84/drbdadm.8 ${D}${mandir}/man8/drbdadm-8.4.8
    install -m 644 ${S}/documentation/v9/drbdadm.8 ${D}${mandir}/man8/drbdadm-9.0.8
    ln -sf drbdadm-8.4.8 ${D}${mandir}/man8/drbdadm.8

    install -m 644 ${S}/documentation/v83/drbddisk.8 ${D}${mandir}/man8/drbddisk-8.3.8
    install -m 644 ${S}/documentation/v84/drbddisk.8 ${D}${mandir}/man8/drbddisk-8.4.8
    ln -sf drbddisk-8.4.8 ${D}${mandir}/man8/drbddisk.8

    install -m 644 ${S}/documentation/v83/drbdmeta.8 ${D}${mandir}/man8/drbdmeta-8.3.8
    install -m 644 ${S}/documentation/v84/drbdmeta.8 ${D}${mandir}/man8/drbdmeta-8.4.8
    install -m 644 ${S}/documentation/v9/drbdmeta.8 ${D}${mandir}/man8/drbdmeta-9.0.8
    ln -sf drbdmeta-8.4.8 ${D}${mandir}/man8/drbdmeta.8

    install -m 644 ${S}/documentation/v9/drbdmon.8 ${D}${mandir}/man8/drbdmon-9.0.8
    ln -sf drbdmon-9.0.8 ${D}${mandir}/man8/drbdmon.8

    install -m 644 ${S}/documentation/v83/drbdsetup.8 ${D}${mandir}/man8/drbdsetup-8.3.8
    install -m 644 ${S}/documentation/v84/drbdsetup.8 ${D}${mandir}/man8/drbdsetup-8.4.8
    install -m 644 ${S}/documentation/v9/drbdsetup.8 ${D}${mandir}/man8/drbdsetup-9.0.8
    ln -sf drbdsetup-8.4.8 ${D}${mandir}/man8/drbdsetup.8

    # don't install empty /var/lock to avoid conflict with base-files
    rm -rf ${D}${localstatedir}/lock
}

RDEPENDS_${PN} += "bash perl-module-getopt-long perl-module-exporter perl-module-constant perl-module-overloading perl-module-exporter-heavy"

# The drbd items are explicitly put under /lib when installed.
#
FILES_${PN} += "/run"
FILES_${PN} += "${nonarch_base_libdir}/drbd \
                ${nonarch_libdir}/drbd \
                ${nonarch_libdir}/tmpfiles.d"
FILES_${PN} += "${datadir}/* ${libdir}/* ${base_libdir}/modules-load.d/*"
FILES_${PN}-dbg += "${nonarch_base_libdir}/drbd/.debug"
