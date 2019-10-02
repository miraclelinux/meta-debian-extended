SUMMARY = "module to load URI::Escape::XS preferentially over URI::Escape"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=d94754f791e3e623f42113268bd2762f"

DEPENDS += "perl"
RDEPENDS_${PN} += "liburi-escape-xs-perl"

inherit debian-package
require recipes-debian/sources/libany-uri-escape-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Any-URI-Escape-${PV}"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += "libtest-needs-perl perl-module-test-more"

BBCLASSEXTEND = "native"
