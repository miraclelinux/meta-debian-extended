# base recipe: meta-oe/recipes-crypto/libsodium/libsodium_1.0.17.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "The Sodium crypto library"
HOMEPAGE = "http://libsodium.org/"
BUGTRACKER = "https://github.com/jedisct1/libsodium/issues"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=47203c753972e855179dfffe15188bee"

inherit debian-package
require recipes-debian/sources/libsodium.inc
DEBIAN_QUILT_PATCHES = ""

inherit autotools

BBCLASSEXTEND = "native nativesdk"
