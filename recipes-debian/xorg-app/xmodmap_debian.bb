# base recipe: meta/recipes-graphics/xorg-app/xmodmap_1.0.10.bb
# base branch: warrior
# base commit: 3b3d520710c71bfe1fd724e1beb2dccc461485bc

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
# This application is provided by x11-xserver-utils source package in Debian.
BPN = "x11-xserver-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

SUMMARY = "Utility for modifying keymaps and pointer button mappings in X"

DESCRIPTION = "The xmodmap program is used to edit and display the \
keyboard modifier map and keymap table that are used by client \
applications to convert event keycodes into keysyms. It is usually run \
from the user's session startup script to configure the keyboard \
according to personal tastes."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://xmodmap/COPYING;md5=272c17e96370e1e74773fa22d9989621"

EXTRA_OECONF += "--host=${BUILD_SYS}"

TARGET_APP = "xmodmap"

do_configure () {
    cd ${S}/${TARGET_APP}; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/${TARGET_APP} ${EXTRA_OEMAKE} 
}

do_install() {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}' install prefix='/usr'
}
