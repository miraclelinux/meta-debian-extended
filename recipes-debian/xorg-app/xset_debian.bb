# base recipe: meta/recipes-graphics/xorg-app/xset_1.2.4.bb
# base branch: warrior
# base commit: ca0a42695375fd43357ee753ae9c013128153391

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
# This application is provided by x11-xserver-utils source package in Debian.
BPN = "x11-xserver-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"
FILESEXTRAPATHS_prepend := "${THISDIR}/xset:"

SUMMARY = "Utility for setting various user preference options of the display"

DESCRIPTION = "xset is a utility that is used to set various user \
preference options of the display."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://xset/COPYING;md5=bea81cc9827cdf1af0e12c2b8228cf8d"
# Remove libxxf86misc
# DEPENDS += "libxext libxxf86misc libxmu libxau"
DEPENDS += "libxext libxmu libxau"

SRC_URI += "file://0001-Add-disable-xkb-option.patch \
	file://x11.patch"

CFLAGS += "-D_GNU_SOURCE"
EXTRA_OECONF = "--disable-xkb --without-fontcache"
EXTRA_OECONF += "--host=${BUILD_SYS}"

TARGET_APP = "xset"
# Set command's version instead of using debian package version.
PV = "1.2.4"

do_configure () {
    cd ${S}/${TARGET_APP}; autoreconf -f -i -s; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/${TARGET_APP} ${EXTRA_OEMAKE}
}

do_install() {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}' install prefix='/usr'
}
