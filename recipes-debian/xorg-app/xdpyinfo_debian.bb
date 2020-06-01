# base recipe: meta/recipes-graphics/xorg-app/xdpyinfo_1.3.2.bb
# base branch: warrior
# base commit: b88a24ae5bb91e3d9e1d2a8d0f908280398f7c5a

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
# This application is provided by x11-utils source package in Debian.
BPN = "x11-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"
FILESEXTRAPATHS_prepend := "${THISDIR}/xdpyinfo:"

SUMMARY = "Display information utility for X"

DESCRIPTION = "Xdpyinfo is a utility for displaying information about an \
X server. It is used to examine the capabilities of a server, the \
predefined values for various parameters used in communicating between \
clients and the server, and the different types of screens and visuals \
that are available."

LIC_FILES_CHKSUM = "file://xdpyinfo/COPYING;md5=f3d09e6b9e203a1af489e16c708f4fb3"
DEPENDS += "libxtst libxext libxxf86vm libxxf86dga libxi libxrender libxinerama libdmx libxau libxcomposite"

SRC_URI += "file://0001-Add-disable-xkb-option.patch"

EXTRA_OECONF = "--disable-xkb"
EXTRA_OECONF += "--host=${BUILD_SYS}"

TARGET_APP = "xdpyinfo"
# Set command's version instead of using debian package version.
PV = "1.3.2"

do_configure () {
    cd ${S}/${TARGET_APP}; autoreconf -f -i -s; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/${TARGET_APP} ${EXTRA_OEMAKE}
}

do_install() {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}' install prefix='/usr'
}

