SUMMARY = "Active Network Smashing Tool"
DESCRIPTION = " \
hping3 is a network tool able to send custom TCP/IP packets and to \
display target replies like ping do with ICMP replies. hping3 can handle \
fragmentation, and almost arbitrary packet size and content, using the \
command line interface. \
"

inherit debian-package
require recipes-debian/sources/hping3.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/${PN}-${REPACK_PV}"

LICENSE="GPLv2 & BSD-3-Clause & BSD-4-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=3728a6c4c9630a9e796ad4b82dacd887"

inherit autotools-brokensep

DEPENDS = "libpcap tcl"

do_configure_append() {
    sed -e '/^#ifndef$/ s|$| __LITTLE_ENDIAN_BITFIELD|' \
        -e '/^#define$/ s|$| __LITTLE_ENDIAN_BITFIELD|' \
        -i ${S}/byteorder.h

    sed -e '/^CC=/ s|=.*$|=${CC}|' \
        -e '/^AR=/ s|=.*$|=${AR}|' \
        -e '/^RANLIB=/ s|=.*$|=${RANLIB}|' \
        -e '/^CCOPT=/ s|=.*$|=${CFLAGS}|' \
        -e '/\t\.\/hping3 -v/ d' \
        -i ${S}/Makefile
}

do_compile() {
    oe_runmake prefix=${RECIPE_SYSROOT}
}

do_install_prepend() {
    install -d ${D}/usr/sbin
}
