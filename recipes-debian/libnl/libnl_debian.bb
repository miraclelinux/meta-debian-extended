# base recipe: meta/recipes-support/libnl/libnl_3.4.0.bb
# base branch: warrior
# base commit: f7a97647ffa08c9991a1bc85301c459866311925

SUMMARY = "A library for applications dealing with netlink sockets"
HOMEPAGE = "http://www.infradead.org/~tgr/libnl/"
SECTION = "libs/network"

PE = "1"

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "flex-native bison-native"

BPN = "libnl3"
inherit debian-package
require recipes-debian/sources/${BPN}.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/libnl-${PV}"

FILESPATH_append = ":${COREBASE}/meta/recipes-support/libnl/libnl"
SRC_URI += " \
           file://fix-pc-file.patch \
           file://0001-PATCH-fix-libnl-3.4.0-musl-compile-problem.patch \
"

UPSTREAM_CHECK_URI = "https://github.com/thom311/${BPN}/releases"

SRC_URI[md5sum] = "8f71910c03db363b41e2ea62057a4311"
SRC_URI[sha256sum] = "b7287637ae71c6db6f89e1422c995f0407ff2fe50cecd61a312b6a9b0921f5bf"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/libnl-3.so.* \
               ${libdir}/libnl.so.* \
               ${sysconfdir}"
RREPLACES_${PN} = "libnl2"
RCONFLICTS_${PN} = "libnl2"
FILES_${PN}-dev += "${libdir}/libnl/cli/*/*.la"
FILES_${PN}-staticdev += "${libdir}/libnl/cli/*/*.a"

PACKAGES += "${PN}-cli ${PN}-genl ${PN}-idiag ${PN}-nf ${PN}-route ${PN}-xfrm"
FILES_${PN}-cli   = "${libdir}/libnl-cli-3.so.* \
                     ${libdir}/libnl/cli/*/*.so \
                     ${bindir}/genl-ctrl-list \
                     ${bindir}/idiag-socket-details \
                     ${bindir}/nf-* \
                     ${bindir}/nl-*"
FILES_${PN}-genl  = "${libdir}/libnl-genl-3.so.* \
                     ${libdir}/libnl-genl.so.*"
FILES_${PN}-idiag = "${libdir}/libnl-idiag-3.so.*"
FILES_${PN}-nf    = "${libdir}/libnl-nf-3.so.*"
FILES_${PN}-route = "${libdir}/libnl-route-3.so.*"
FILES_${PN}-xfrm  = "${libdir}/libnl-xfrm-3.so.*"
RREPLACES_${PN}-genl = "libnl-genl2"
RCONFLICTS_${PN}-genl = "libnl-genl2"
