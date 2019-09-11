# base recipe: meta-networking/recipes-filter/libnetfilter/libnetfilter-acct_1.0.3.bb
# base branch: warrior
# base commit: 93de05ce5f39187d4cfb390ecdc7901d8d168c60

SUMMARY = "libnetfilter_acct accounting infrastructure."
DESCRIPTION = "libnetfilter_acct is the userspace library providing interface to extended accounting infrastructure."
HOMEPAGE = "http://netfilter.org/projects/libnetfilter_acct/index.html"
SECTION = "libs"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"
DEPENDS = "libnfnetlink libmnl"

inherit debian-package
require recipes-debian/sources/libnetfilter-acct.inc

SRC_URI += " file://0001-libnetfilter-acct-Declare-the-define-visivility-attribute-together.patch \
"
DEBIAN_UNPACK_DIR = "${WORKDIR}/libnetfilter_acct-${PV}"
S = "${WORKDIR}/libnetfilter_acct-${PV}"

inherit autotools pkgconfig
