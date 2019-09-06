# base recipe: meta-perl/recipes-perl/libdigest/libdigest-sha1-perl_2.13.bb
# base branch: warrior
# base commit: e4c1f724441af1c518d6bfe54457e09c1504dd21

SUMMARY = "Perl interface to the SHA algorithm "
DESCRIPTION = "Digest::SHA - Perl interface to the SHA algorithm"
SECTION = "libs"

LICENSE = "Artistic-1.0|GPLv1+"
LIC_FILES_CHKSUM = "file://README;beginline=10;endline=14;md5=b2f1cfeb9e92bb04b00720bfb8ea4c44"

inherit debian-package
require recipes-debian/sources/libdigest-sha-perl.inc

SRC_URI += " file://run-ptest \
           "

RDEPENDS_${PN} += "\
    perl-module-fcntl \
    perl-module-getopt-long \
    perl-module-integer \
"

DEBIAN_UNPACK_DIR = "${WORKDIR}/Digest-SHA-${PV}"
S = "${WORKDIR}/Digest-SHA-${PV}"

inherit cpan ptest

DEBIAN_QUILT_PATCHES = ""

do_install_ptest () {
    cp -r ${B}/t ${D}${PTEST_PATH}
}

BBCLASSEXTEND="native"

