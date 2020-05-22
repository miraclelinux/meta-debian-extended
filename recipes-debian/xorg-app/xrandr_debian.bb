# base recipe: meta/recipes-graphics/xorg-app/xrandr_1.5.0.bb
# base branch: warrior
# base commit: 376bba7d354d08318ed00f158424e5351abe41d3

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
# This application is provided by x11-utils source package in Debian.
BPN = "x11-xserver-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"
#FILESEXTRAPATHS_prepend := "${THISDIR}/xrandr:"

SUMMARY = "XRandR: X Resize, Rotate and Reflect extension command"

DESCRIPTION = "Xrandr is used to set the size, orientation and/or \
reflection of the outputs for a screen. It can also set the screen \
size."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://xrandr/COPYING;md5=fe1608bdb33cf8c62a4438f7d34679b3"
DEPENDS += "libxrandr libxrender"
PE = "1"

EXTRA_OECONF = " \
    --target=${TARGET_SYS} \
    --host=${BUILD_SYS} \
"

TARGET_APP = "xrandr"
# Set command's version instead of using debian package version.
PV = "1.5.0"

do_configure () {
    cd ${S}/${TARGET_APP}; autoreconf -f -i -s; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/${TARGET_APP} ${EXTRA_OEMAKE}
}

do_install() {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}' install prefix='/usr'
}
