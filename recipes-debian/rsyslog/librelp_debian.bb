# base recipe: meta-openembedded/meta-oe/recipes-extended/rsyslog/librelp_1.4.0.bb
# base branch: warrior
# base commit: 666c23a48a93ad38443a0c70f8d5b1fa12cafd86

SUMMARY = "A reliable logging library"
HOMEPAGE = "https://github.com/rsyslog/librelp"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=1fb9c10ed9fd6826757615455ca893a9"

inherit debian-package
require recipes-debian/sources/librelp.inc

DEPENDS = "gmp nettle libidn zlib gnutls openssl"
DEBIAN_QUILT_PATCHES = ""

inherit autotools pkgconfig
