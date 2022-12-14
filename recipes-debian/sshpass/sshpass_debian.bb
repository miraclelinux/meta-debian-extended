#base recipe: meta-openembedded/meta-networking/recipes-connectivity/sshpass/sshpass_1.09.bb
#base branch: langdale
#base commit: c5668905a6d8a78fb72c2cbf8b20e91e686ceb86
DESCRIPTION = "Non-interactive ssh password auth"
HOMEPAGE = "http://sshpass.sourceforge.net/"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit autotools debian-package
require recipes-debian/sources/sshpass.inc

DEBIAN_QUILT_PATCHES = ""

do_install_append() {
    install -d ${D}${docdir}/sshpass
    install -m 644 ${S}/debian/changelog ${D}${docdir}/sshpass/changelog.Debian
    install -m 644 ${S}/ChangeLog ${D}${docdir}/sshpass/changelog
    install -m 644 ${S}/debian/copyright ${D}${docdir}/sshpass
}
