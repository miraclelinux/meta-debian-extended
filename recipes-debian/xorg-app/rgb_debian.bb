# base recipe: meta/recipes-graphics/xorg-app/rgb_1.0.6.bb
# base branch: warrior
# base commit: 4ba923dbac2845aca5b4312a5f278ad886a7acc7

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
# This application is provided by x11-xserver-utils source package in Debian.
BPN = "x11-xserver-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

SUMMARY = "X11 color name database"
DESCRIPTION = "This package includes both the list mapping X color names \
to RGB values (rgb.txt) and, if configured to use a database for color \
lookup, the rgb program to convert the text file into the binary database \
format."

DEPENDS += " xorgproto util-macros libxmu"
LIC_FILES_CHKSUM = "file://rgb/COPYING;md5=ef598adbe241bd0b0b9113831f6e249a"

FILES_${PN} += "${datadir}/X11"

EXTRA_OECONF += "--host=${BUILD_SYS}"

TARGET_APP = "xset"

# Set command's version instead of using debian package version.
PV = "1.0.6"

do_configure () {
    # cd ${S}/${TARGET_APP}; autoreconf -f -i -s; ./configure ${EXTRA_OECONF}
    cd ${S}/${TARGET_APP}; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/${TARGET_APP} ${EXTRA_OEMAKE}
}

do_install() {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}' install prefix='/usr'
}
