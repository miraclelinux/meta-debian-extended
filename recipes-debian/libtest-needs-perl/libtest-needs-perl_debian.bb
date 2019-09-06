# base recipe: poky/meta/recipes-devtools/perl/libtest-needs-perl_0.002005.bb
# base branch: warrior
# base commit: 01b8a8b54bc569e5ef3f5e6fc6abcee365ab25d9

SUMMARY = "Skip tests when modules not available"
DESCRIPTION = "Skip test scripts if modules are not available. \
The requested modules will be loaded, and optionally have their versions \
checked. If the module is missing, the test script will be skipped. Modules \
that are found but fail to compile will exit with an error rather than skip."

HOMEPAGE = "https://metacpan.org/release/Test-Needs"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

CPAN_NAME = "Test-Needs"
CPAN_AUTHOR = "HAARG"

LIC_FILES_CHKSUM = "file://README;md5=3f3ccd21a0a48aa313db212cc3b1bc09;beginline=81;endline=82"

DEPENDS += "perl"

inherit debian-package
require recipes-debian/sources/libtest-needs-perl.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}${PV}"

S = "${WORKDIR}/${CPAN_NAME}-${PV}"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += "perl-module-test-more"

BBCLASSEXTEND = "native"

do_debian_patch(){
}
