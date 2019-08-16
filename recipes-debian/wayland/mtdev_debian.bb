# base recipe: meta/recipes-graphics/wayland/mtdev_1.1.5.bb
# base branch: warrior
# base commit: f874ac6e873e3d2195b671d9eea24aca3ff69cbd

SUMMARY = "Multitouch Protocol Translation Library"

DESCRIPTION = "mtdev is a library which transforms all variants of kernel \
multitouch events to the slotted type B protocol. The events put into mtdev may \
be from any MT device, specifically type A without contact tracking, type A with \
contact tracking, or type B with contact tracking"

inherit debian-package
require recipes-debian/sources/mtdev.inc
DEBIAN_QUILT_PATCHES = ""

HOMEPAGE = "http://bitmath.org/code/mtdev/"
SECTION = "libs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=ea6bd0268bb0fcd6b27698616ceee5d6"

inherit autotools pkgconfig
