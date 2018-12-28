SUMMARY = "Free Implementation of the Unicode Bidirectional Algorithm"
SECTION = "libs"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=a916467b91076e631dd8edb7424769c7"

inherit debian-package
require recipes-debian/sources/fribidi.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-support/fribidi/fribidi"

SRC_URI += " \
           file://meson.patch"

UPSTREAM_CHECK_URI = "https://github.com/${BPN}/${BPN}/releases"

inherit meson lib_package pkgconfig

CVE_PRODUCT = "gnu_fribidi"

BBCLASSEXTEND = "native nativesdk"
