# base recipe: meta/recipes-graphics/xorg-app/mkfontdir_1.0.7.bb
# base branch: warrior
# base commit: cc1f50fca85dc48a237a518b8afe8563225ea8fa

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package

# This application is provided by xfont-utils source package in Debian.
BPN = "xfonts-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

SUMMARY = "A program to create an index of X font files in a directory"

DESCRIPTION = "For each directory argument, mkfontdir reads all of the \
font files in the directory. The font names and related data are written \
out to the files \"fonts.dir\", \"fonts.scale\", and \"fonts.alias\".  \
The X server and font server use these files to find the available font \
files."

RDEPENDS_${PN} += "mkfontscale"
RDEPENDS_${PN}_class-native += "mkfontscale-native"

BBCLASSEXTEND = "native"

LIC_FILES_CHKSUM = "file://mkfontdir/COPYING;md5=b4fcf2b90cadbfc15009b9e124dc3a3f"

EXTRA_OECONF = " \
    --target=${TARGET_SYS} \
    --host=${BUILD_SYS} \
"

TARGET_APP = "mkfontdir"

do_configure () {
    cd ${S}/${TARGET_APP}; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/${TARGET_APP} ${EXTRA_OEMAKE} 
}

do_install_class-target() {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}' install prefix='/usr'
}

do_install_class-native () {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}/${STAGING_DIR_NATIVE}' install prefix='/usr'
}

