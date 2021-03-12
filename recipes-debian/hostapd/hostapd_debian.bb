# base recipe meta-oe/recipes-connectivity/hostapd/hostapd_2.9.bb
# base branch: master
# base commit: 589aa162cead42acdd7e8dbd7c0243b95e341f19

SUMMARY = "User space daemon for extended IEEE 802.11 management"
HOMEPAGE = "http://w1.fi/hostapd/"
SECTION = "kernel/userland"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://hostapd/README;md5=1ec986bec88070e2a59c68c95d763f89"

DEPENDS = "libnl openssl"

BPN = "wpa"
inherit debian-package
require recipes-debian/sources/wpa.inc


FILESPATH_append = ":${THISDIR}/${PN}:"
SRC_URI += " \
    file://defconfig \
    file://init \
    file://hostapd.service \
"

inherit update-rc.d systemd pkgconfig 

CONFLICT_DISTRO_FEATURES = "openssl-no-weak-ciphers"

INITSCRIPT_NAME = "hostapd"

SYSTEMD_SERVICE_${PN} = "hostapd.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

do_configure_append() {
    install -m 0644 ${WORKDIR}/defconfig ${B}/${PN}/.config
}

do_compile() {
    export CFLAGS="-MMD -O2 -Wall -g"
    export EXTRA_CFLAGS="${CFLAGS}"
    cd ${B}/${PN} ; make V=1
}

do_install() {
    install -d ${D}${sbindir} ${D}${sysconfdir}/init.d ${D}${systemd_unitdir}/system/
    install -m 0644 ${B}/${PN}/hostapd.conf ${D}${sysconfdir}
    install -m 0755 ${B}/${PN}/hostapd ${D}${sbindir}
    install -m 0755 ${B}/${PN}/hostapd_cli ${D}${sbindir}
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/hostapd
    install -m 0644 ${WORKDIR}/hostapd.service ${D}${systemd_unitdir}/system/
    sed -i -e 's,@SBINDIR@,${sbindir},g' -e 's,@SYSCONFDIR@,${sysconfdir},g' ${D}${systemd_unitdir}/system/hostapd.service
}

CONFFILES_${PN} += "${sysconfdir}/hostapd.conf"
