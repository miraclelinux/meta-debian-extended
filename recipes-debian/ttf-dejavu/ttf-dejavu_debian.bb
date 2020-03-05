# base recipe: meta-oe/recipes-graphics/ttf-fonts/ttf-dejavu_2.37.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

require ttf.inc

SUMMARY = "DejaVu font - TTF Edition"
HOMEPAGE = "http://dejavu.sourceforge.net/wiki/"
LICENSE = "BitstreamVera"
LIC_FILES_CHKSUM = "file://LICENSE;md5=449b2c30bfe5fa897fe87b8b70b16cfa"

inherit debian-package
require recipes-debian/sources/fonts-dejavu.inc
DEBIAN_QUILT_PATCHES = ""

BPN = "dejavu-fonts"

# all subpackages except ${PN}-common itself rdepends on ${PN}-common
RDEPENDS_${PN}-sans = "${PN}-common"
RDEPENDS_${PN}-sans-mono = "${PN}-common"
RDEPENDS_${PN}-sans-condensed = "${PN}-common"
RDEPENDS_${PN}-serif = "${PN}-common"
RDEPENDS_${PN}-serif-condensed = "${PN}-common"
RDEPENDS_${PN}-mathtexgyre = "${PN}-common"
RDEPENDS_${PN}-common = ""
PR = "r7"

DEPENDS += " fontforge-native libfont-ttf-perl-native libio-string-perl-native"

SRC_URI += " \
           file://30-dejavu-aliases.conf \
"
do_compile_prepend () {
    export PERL5LIB=${STAGING_DIR_NATIVE}/usr/share/perl5
}

do_install_append () {
    install -d ${D}${sysconfdir}/fonts/conf.d/
    install -m 0644 ${WORKDIR}/30-dejavu-aliases.conf ${D}${sysconfdir}/fonts/conf.d/
}

PACKAGES = "\
            ${PN}-sans \
            ${PN}-sans-mono \
            ${PN}-sans-condensed \
            ${PN}-serif \
            ${PN}-serif-condensed \
            ${PN}-mathtexgyre \
            ${PN}-common"
FONT_PACKAGES = "${PN}-sans ${PN}-sans-mono ${PN}-sans-condensed ${PN}-serif ${PN}-serif-condensed ${PN}-mathtexgyre"

FILES_${PN}-sans            = "${datadir}/fonts/truetype/DejaVuSans.ttf ${datadir}/fonts/truetype/DejaVuSans-*.ttf"
FILES_${PN}-sans-mono       = "${datadir}/fonts/truetype/DejaVuSansMono*.ttf"
FILES_${PN}-sans-condensed  = "${datadir}/fonts/truetype/DejaVuSansCondensed*.ttf"
FILES_${PN}-serif           = "${datadir}/fonts/truetype/DejaVuSerif.ttf ${datadir}/fonts/truetype/DejaVuSerif-*.ttf"
FILES_${PN}-serif-condensed = "${datadir}/fonts/truetype/DejaVuSerifCondensed*.ttf"
FILES_${PN}-mathtexgyre     = "${datadir}/fonts/truetype/DejaVuMathTeXGyre.ttf"
FILES_${PN}-common          = "${sysconfdir}"

