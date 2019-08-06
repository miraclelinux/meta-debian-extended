# base recipe: meta/recipes-support/rng-tools/rng-tools_5.bb
# base branch: warrior
# base commit: 81fc280bae13c573d8b82b05ad5eb18e7882ad24

SUMMARY = "Random number generator daemon"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=0b6f033afe6db235e559456585dc8cdc"

inherit debian-package
require recipes-debian/sources/rng-tools5.inc

DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${PN}5-${PV}"
BPN = "${PN}5"

FILESPATH_append = ":${COREBASE}/meta/recipes-support/rng-tools/rng-tools:"
SRC_URI += "file://0001-If-the-libc-is-lacking-argp-use-libargp.patch \
           file://0002-Add-argument-to-control-the-libargp-dependency.patch \
           file://underquote.patch \
           file://rng-tools-5-fix-textrels-on-PIC-x86.patch \
           file://read_error_msg.patch \
           file://init \
           file://default \
           file://rngd.service \
"

inherit autotools update-rc.d systemd

PACKAGECONFIG = "libgcrypt"
PACKAGECONFIG_libc-musl = "libargp"
PACKAGECONFIG[libargp] = "--with-libargp,--without-libargp,argp-standalone,"
PACKAGECONFIG[libgcrypt] = "--with-libgcrypt,--without-libgcrypt,libgcrypt,"

do_install_append() {
    # Only install the init script when 'sysvinit' is in DISTRO_FEATURES.
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d "${D}${sysconfdir}/init.d"
        install -m 0755 ${DEBIAN_UNPACK_DIR}/debian/rngd.init ${D}${sysconfdir}/init.d/rng-tools
        sed -i -e 's,/etc/,${sysconfdir}/,' -e 's,/usr/sbin/,${sbindir}/,' \
            ${D}${sysconfdir}/init.d/rng-tools

        install -d "${D}${sysconfdir}/default"
        install -m 0644 ${WORKDIR}/default ${D}${sysconfdir}/default/rng-tools
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${DEBIAN_UNPACK_DIR}/debian/rngd.service ${D}${systemd_unitdir}/system
        sed -i -e 's,@SBINDIR@,${sbindir},g' ${D}${systemd_unitdir}/system/rngd.service
    fi
}

INITSCRIPT_NAME = "rng-tools"
INITSCRIPT_PARAMS = "start 03 2 3 4 5 . stop 30 0 6 1 ."

SYSTEMD_SERVICE_${PN} = "rngd.service"
