# base recipe: meta/recipes-graphics/xorg-lib/libxxf86vm_1.1.4.bb
# base branch: warrior
# base commit: 4ba923dbac2845aca5b4312a5f278ad886a7acc7

SUMMARY = "XFree86-VM: XFree86 video mode extension library"

DESCRIPTION = "libXxf86vm provides an interface to the \
XFree86-VidModeExtension extension, which allows client applications to \
get and set video mode timings in extensive detail.  It is used by the \
xvidtune program in particular."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc
# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxxf86vm.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=fa0b9c462d8f2f13eba26492d42ea63d"

DEPENDS += "libxext xorgproto"

PE = "1"

XORG_PN = "libXxf86vm"
