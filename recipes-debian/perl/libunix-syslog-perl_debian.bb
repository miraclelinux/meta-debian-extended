SUMMARY = "Perl interface to the UNIX syslog(3) calls"

SECTION = "libs"
LICENSE = "Artistic-2.0 | GPLv2"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=ac2f5770c103b5c87195273f068c75a7"

DEPENDS += "perl"

inherit debian-package
require recipes-debian/sources/libunix-syslog-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Unix-Syslog-${PV}"

inherit cpan

BBCLASSEXTEND = "native"
