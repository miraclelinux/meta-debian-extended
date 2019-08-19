# base recipe: meta/recipes-graphics/xorg-lib/libxcursor_1.2.0.bb
# base branch: warrior
# base commit: 2965a9a5ed83a3a08acdbe03580261bd35538427

SUMMARY = "Xcursor: X Cursor management library"

DESCRIPTION = "Xcursor is a simple library designed to help locate and \
load cursors. Cursors can be loaded from files or memory. A library of \
common cursors exists which map to the standard X cursor names. Cursors \
can exist in several sizes and the library automatically picks the best \
size."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxcursor.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"

LICENSE = "MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=8902e6643f7bcd7793b23dcd5d8031a4"

DEPENDS += "libxrender libxfixes"

XORG_PN = "libXcursor"

BBCLASSEXTEND = "native nativesdk"
