# base recipe: meta/recipes-graphics/xorg-lib/libpciaccess_0.14.bb
# base branch: warrior
# base commit: 800f4a0c192f05270490ca10b28357e83a36d978

SUMMARY = "Generic PCI access library for X"

DESCRIPTION = "libpciaccess provides functionality for X to access the \
PCI bus and devices in a platform-independent way."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libpciaccess.inc
DEBIAN_PATCH_TYPE = "nopatch"
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/xorg-lib/libpciaccess"

SRC_URI += "\
            file://0004-Don-t-include-sys-io.h-on-arm.patch \
"

LICENSE = "MIT & MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=277aada5222b9a22fbf3471ff3687068"

REQUIRED_DISTRO_FEATURES = ""

BBCLASSEXTEND = "native nativesdk"
