require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxxf86dga.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"

SUMMARY = "XFree86-DGA: XFree86 Direct Graphics Access extension library"

DESCRIPTION = "libXxf86dga provides the XFree86-DGA extension, which \
allows direct graphics access to a framebuffer-like region, and also \
allows relative mouse reporting, et al.  It is mainly used by games and \
emulators for games."

LIC_FILES_CHKSUM = "file://COPYING;md5=abb99ac125f84f424a4278153988e32f"

DEPENDS += "libxext"

PE = "1"

XORG_PN = "libXxf86dga"
