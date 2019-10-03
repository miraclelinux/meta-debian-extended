SUMMARY = "SGML infrastructure and SGML catalog file support"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=1201cda42eb8554eceb46b69f50ba9ea"

inherit debian-package
require recipes-debian/sources/sgml-base.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_QUILT_PATCHES = ""

do_configure[noexec] = "1"

do_install() {
    oe_runmake "prefix=${D}/usr" install
}

FILES_${PN} += "/usr/local/share/sgml/* /usr/share/sgml/*"

RDEPENDS_${PN} = "perl"
