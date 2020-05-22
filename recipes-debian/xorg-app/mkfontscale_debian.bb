# base recipe: meta/recipes-graphics/xorg-app/mkfontscale_1.1.3.bb
# base branch: warrior
# base commit: 4ba923dbac2845aca5b4312a5f278ad886a7acc7

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package

# This application is provided by xfont-utils source package in Debian.
BPN = "xfonts-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

SUMMARY = "A program to create an index of scalable font files for X"

DESCRIPTION = "For each directory argument, mkfontscale reads all of the \
scalable font files in the directory. For every font file found, an X11 \
font name (XLFD) is generated, and is written together with the file \
name to a file fonts.scale in the directory.  The resulting fonts.scale \
is used by the mkfontdir program."

DEPENDS = "util-macros-native zlib libfontenc freetype xorgproto"

# Set command's version instead of using debian package version.
PV = "1.1.3"
BBCLASSEXTEND = "native"

LIC_FILES_CHKSUM = "file://mkfontscale/COPYING;md5=2e0d129d05305176d1a790e0ac1acb7f"

EXTRA_OECONF = " \
    --target=${TARGET_SYS} \
    --host=${BUILD_SYS} \
"

TARGET_APP = "mkfontscale"

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
