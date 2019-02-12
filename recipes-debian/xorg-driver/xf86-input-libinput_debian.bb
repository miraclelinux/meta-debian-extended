require recipes-graphics/xorg-driver/xorg-driver-input.inc

SUMMARY = "Generic input driver for the X.Org server based on libinput"
LIC_FILES_CHKSUM = "file://COPYING;md5=5e6b20ea2ef94a998145f0ea3f788ee0"

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/xserver-xorg-input-libinput.inc
BPN = "xserver-xorg-input-libinput"
DEBIAN_UNPACK_DIR = "${WORKDIR}/xf86-input-libinput-${@d.getVar('PV', True)}"
DEBIAN_PATCH_TYPE = "nopatch"

DEPENDS += "libinput"

FILES_${PN} += "${datadir}/X11/xorg.conf.d"
