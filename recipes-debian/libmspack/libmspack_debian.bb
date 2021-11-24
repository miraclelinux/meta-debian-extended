# base recipe: recipes-security/libmspack/libmspack_0.10.1.bb
# base branch: warrior
# base commit: 4f7be0d252f68d8e8d442a7ed8c6e8a852872d28

SUMMARY = "A library for Microsoft compression formats"
HOMEPAGE = "http://www.cabextract.org.uk/libmspack/"
SECTION = "lib"
LICENSE = "LGPL-2.1"
DEPENDS = ""

LIC_FILES_CHKSUM = "file://COPYING.LIB;beginline=1;endline=2;md5=5b1fd1f66ef926b3c8a5bb00a72a28dd"


inherit autotools

DEBIAN_UNPACK_DIR = "${WORKDIR}/libmspack-0.10.1alpha"
inherit debian-package
require recipes-debian/sources/libmspack.inc
DEBIAN_QUILT_PATCHES = ""
