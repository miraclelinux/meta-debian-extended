SUMMARY = "Perl module for inspecting perl's symbol table"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=20421635090217ec506f68bfd5596a66"

DEPENDS += "perl"

inherit debian-package
require recipes-debian/sources/libdevel-symdump-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Devel-Symdump-${PV}"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += "libtest-needs-perl perl-module-test-more"

BBCLASSEXTEND = "native"
