SUMMARY = "Perl module for TrueType font hacking"
LICENSE = "Artistic-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ed576aa4482ed73ca38441879ec3e83e"

inherit debian-package
require recipes-debian/sources/libfont-ttf-perl.inc
DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Font-TTF-${PV}"

BBCLASSEXTEND = "native"

# nothing to compile
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SYSROOT_DIRS_NATIVE += "${datadir}"

do_install () {
    install -d ${D}${datadir}/perl5
    cp -r ${S}/lib/* ${D}${datadir}/perl5/
}
