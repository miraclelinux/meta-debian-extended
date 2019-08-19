# base recipe: meta/recipes-graphics/xorg-app/xinput_1.6.2.bb
# base branch: warrior
# base commit: b7cb308a23a9c7f9002c03a0362f51897a132945

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/xinput.inc
DEBIAN_PATCH_TYPE = "nopatch"

SUMMARY = "Runtime configuration and test of XInput devices"

DESCRIPTION = "Xinput is an utility for configuring and testing XInput devices"

LIC_FILES_CHKSUM = "file://COPYING;md5=881525f89f99cad39c9832bcb72e6fa5"

DEPENDS += " libxi libxrandr libxinerama"
