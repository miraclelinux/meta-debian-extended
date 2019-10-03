SUMMARY = "Perl implementation of Readline libraries"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=ddac6c42e17c7d26b1273872bd0b2c62"

DEPENDS += "perl"

inherit debian-package
require recipes-debian/sources/libterm-readline-perl-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Term-ReadLine-Perl-${PV}"

inherit cpan

BBCLASSEXTEND = "native"
