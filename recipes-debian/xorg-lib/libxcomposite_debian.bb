# base recipe: meta/recipes-graphics/xorg-lib/libxcomposite_0.4.5.bb
# base branch: warrior
# base commit: feaf218deafb700a5e5901cf6981743f3c72983a

SUMMARY = "Xcomposite: X Composite extension library"

DESCRIPTION = "The composite extension provides three related \
mechanisms: per-hierarchy storage, automatic shadow update, and external \
parent.  In per-hierarchy storage, the rendering of an entire hierarchy \
of windows is redirected to off-screen storage.  In automatic shadow \
update, when a hierarchy is rendered off-screen, the X server provides \
an automatic mechanism for presenting those contents within the parent \
window.  In external parent, a mechanism for providing redirection of \
compositing transformations through a client."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxcomposite.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/xorg-lib/libxcomposite"

LICENSE = "MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=3f2907aad541f6f226fbc58cc1b3cdf1"

DEPENDS += " xorgproto virtual/libx11 libxfixes libxext"
PROVIDES = "xcomposite"
BBCLASSEXTEND = "native nativesdk"

XORG_PN = "libXcomposite"

SRC_URI += " file://change-include-order.patch"
