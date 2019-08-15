# base recipe: meta/recipes-connectivity/iw/iw_5.0.1.bb
# base branch: master
# base commit: 9dfbb877c7801afa126bb50f6556401a2d4782d1

SUMMARY = "nl80211 based CLI configuration utility for wireless devices"
DESCRIPTION = "iw is a new nl80211 based CLI configuration utility for \
wireless devices. It supports almost all new drivers that have been added \
to the kernel recently. "
HOMEPAGE = "http://wireless.kernel.org/en/users/Documentation/iw"
SECTION = "base"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=878618a5c4af25e9b93ef0be1a93f774"

DEPENDS = "libnl"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

DEBIAN_QUILT_PATCHES = ""

FILESPATH_append = ":${COREBASE}/meta/recipes-connectivity/iw/iw"
SRC_URI += "\
    file://0001-iw-version.sh-don-t-use-git-describe-for-versioning.patch \
    file://separate-objdir.patch \
"

inherit pkgconfig

EXTRA_OEMAKE = "\
    -f '${S}/Makefile' \
    \
    'PREFIX=${prefix}' \
    'SBINDIR=${sbindir}' \
    'MANDIR=${mandir}' \
"
B = "${WORKDIR}/build"

do_install() {
    oe_runmake 'DESTDIR=${D}' install
}
