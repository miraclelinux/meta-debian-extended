# base recipe: meta/recipes-graphics/xorg-lib/libxt_1.1.5.bb
# base branch: warrior
# base commit: 62a36938e5c5673eb6168338fd177e67d0fa973f

SUMMARY = "Xt: X Toolkit Intrinsics library"

DESCRIPTION = "The Intrinsics are a programming library tailored to the \
special requirements of user interface construction within a network \
window system, specifically the X Window System. The Intrinsics and a \
widget set make up an X Toolkit. The Intrinsics provide the base \
mechanism necessary to build a wide variety of interoperating widget \
sets and application environments. The Intrinsics are a layer on top of \
Xlib, the C Library X Interface. They extend the fundamental \
abstractions provided by the X Window System while still remaining \
independent of any particular user interface policy or style."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxt.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/xorg-lib/libxt"

LICENSE = "MIT & MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=6565b1e0094ea1caae0971cc4035f343"


DEPENDS += "util-linux libxcb libsm virtual/libx11 xorgproto libxdmcp"
PROVIDES = "xt"

XORG_PN = "libXt"

SRC_URI +=  "file://libxt_fix_for_x32.patch \
             file://0001-libXt-util-don-t-link-makestrs-with-target-cflags.patch \
            "

BBCLASSEXTEND = "native nativesdk"

EXTRA_OECONF += "--disable-xkb"
