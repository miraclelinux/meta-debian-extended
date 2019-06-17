#
# base recipe: meta/recipes-graphics/xorg-font/font-util_1.3.1.bb
# base branch: warrior
# base commit: f2d73d6cbb0c1dec049e93124c6bbcdb52f8e757
#

SUMMARY = "X.Org font package creation/installation utilities"

require ${COREBASE}/meta/recipes-graphics/xorg-font/xorg-font-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
BPN = "xfonts-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

#Unicode is MIT
LICENSE = "BSD & MIT"
LIC_FILES_CHKSUM = "file://font-util/COPYING;md5=5df208ec65eb84ce5bb8d82d8f3b9675 \
                    file://font-util/ucs2any.c;endline=28;md5=8357dc567fc628bd12696f15b2a33bcb \
                    file://font-util/bdftruncate.c;endline=26;md5=4f82ffc101a1b165eae9c6998abff937 \
                    file://font-util/map-ISO8859-1;beginline=9;endline=23;md5=1cecb984063248f29ffe5c46f5c04f34"

DEPENDS = "encodings util-macros"
DEPENDS_class-native = "util-macros-native"
RDEPENDS_${PN} = "mkfontdir mkfontscale encodings"
RDEPENDS_${PN}_class-native = ""

BBCLASSEXTEND = "native"

SYSROOT_DIRS_BLACKLIST_remove = "${datadir}/fonts"

EXTRA_OECONF = " \
    --target=${TARGET_SYS} \
    --host=${BUILD_SYS} \
"

do_configure () {
    cd ${S}/font-util; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/font-util ${EXTRA_OEMAKE}
}

do_install_class-target() {
    oe_runmake -C ${S}/font-util 'DESTDIR=${D}' install prefix='/usr'
}

do_install_class-native () {
    oe_runmake -C ${S}/font-util 'DESTDIR=${D}/${STAGING_DIR_NATIVE}' install prefix='/usr'
}

