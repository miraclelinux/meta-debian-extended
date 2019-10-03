SUMMARY = "checker for comprehensiveness of perl module documentation"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=01fd11384c2aa9b31cc0d154cfb8d4b4"

DEPENDS += "perl"
RDEPENDS_${PN} += "libdevel-symdump-perl"

inherit debian-package
require recipes-debian/sources/libpod-coverage-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Pod-Coverage-${PV}"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += "libtest-needs-perl perl-module-test-more"

BBCLASSEXTEND = "native"
