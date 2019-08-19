# base recipe: meta/recipes-graphics/xorg-lib/libxfixes_5.0.3.bb
# base branch: warrior
# base commit: 4ba923dbac2845aca5b4312a5f278ad886a7acc7

SUMMARY = "XFixes: X Fixes extension library"

DESCRIPTION = "X applications have often needed to work around various \
shortcomings in the core X window system.  This extension is designed to \
provide the minimal server-side support necessary to eliminate problems \
caused by these workarounds."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxfixes.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"

LICENSE = "MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=3c1ce42c334a6f5cccb0277556a053e0"

DEPENDS += "virtual/libx11 xorgproto"

XORG_PN = "libXfixes"

BBCLASSEXTEND = "native nativesdk"

