# base recipe: meta/recipes-support/gnutls/libtasn1_4.13.bb
# base branch: warrior
# base commit: a1e2c4e9bde9af41d797a2d0b7e4a3448c47bd64

SUMMARY = "Library for ASN.1 and DER manipulation"
HOMEPAGE = "http://www.gnu.org/software/libtasn1/"

LICENSE = "GPLv3+ & LGPLv2.1+"
LICENSE_${PN}-bin = "GPLv3+"
LICENSE_${PN} = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
                    file://README;endline=8;md5=c3803a3e8ca5ab5eb1e5912faa405351"

inherit debian-package
require recipes-debian/sources/libtasn1-6.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-support/gnutls/libtasn1"

SRC_URI += " \
           file://dont-depend-on-help2man.patch \
           file://0001-stdint.m4-reintroduce-GNULIB_OVERRIDES_WINT_T-check.patch \
           "

DEPENDS = "bison-native"

inherit autotools texinfo lib_package gtk-doc

BBCLASSEXTEND = "native"
