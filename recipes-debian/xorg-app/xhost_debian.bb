require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
# This application is provided by x11-xserver-utils source package in Debian.
BPN = "x11-xserver-utils"
require recipes-debian/sources/${BPN}.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

SUMMARY = "Server access control program for X"

DESCRIPTION = "The xhost program is used to add and delete host names or \
user names to the list allowed to make connections to the X server. In \
the case of hosts, this provides a rudimentary form of privacy control \
and security. Environments which require more sophisticated measures \
should implement the user-based mechanism or use the hooks in the \
protocol for passing other authentication data to the server."

LIC_FILES_CHKSUM = "file://xhost/COPYING;md5=8fbed71dddf48541818cef8079124199"
DEPENDS += "libxmu libxau"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)}"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"

EXTRA_OECONF = " \
    --target=${TARGET_SYS} \
    --host=${BUILD_SYS} \
"

TARGET_APP = "xhost"

do_configure () {
    cd ${S}/${TARGET_APP}; ./configure ${EXTRA_OECONF}
}

do_compile() {
    oe_runmake -C ${S}/${TARGET_APP} ${EXTRA_OEMAKE}
}

do_install() {
    oe_runmake -C ${S}/${TARGET_APP} 'DESTDIR=${D}' install prefix='/usr'
}

