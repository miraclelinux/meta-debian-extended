# base recipe: meta/recipes-graphics/xorg-app/xinit_1.4.1.bb
# base branch: warrior
# base commit: 818d035d750d7d93f30ddcc2517038a4fa73f01e

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

SUMMARY = "X Window System initializer"

DESCRIPTION = "The xinit program is used to start the X Window System \
server and a first client program on systems that cannot start X \
directly from /etc/init or in environments that use multiple window \
systems. When this first client exits, xinit will kill the X server and \
then terminate."

LIC_FILES_CHKSUM = "file://COPYING;md5=18f01e7b39807bebe2b8df101a039b68"

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/xinit.inc
DEBIAN_PATCH_TYPE = "quilt"

DEPENDS += " libxi libxrandr libxinerama"
