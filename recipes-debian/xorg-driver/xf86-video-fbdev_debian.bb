require recipes-graphics/xorg-driver/xorg-driver-video.inc

SUMMARY = "X.Org X server -- fbdev display driver"
DESCRIPTION = "fbdev is an Xorg driver for framebuffer devices. This is a non-accelerated driver."

# clear SRC_URI and other
SRC_URI = ""
PE = ""
INC_PR = ""

inherit debian-package
require recipes-debian/sources/xserver-xorg-video-fbdev.inc
BPN = "xserver-xorg-video-fbdev"
DEBIAN_UNPACK_DIR = "${WORKDIR}/xf86-video-fbdev-0.5.0"
DEBIAN_PATCH_TYPE = "quilt"

LIC_FILES_CHKSUM = "file://COPYING;md5=d8cbd99fff773f92e844948f74ef0df8"
