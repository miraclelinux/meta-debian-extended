# base recipe: meta/recipes-graphics/freetype/freetype_2.9.1.bb
# base branch: warrior
# base commit: a3928b3463ba86f7821f77565c8b2557ac7abbd1

SUMMARY = "Freetype font rendering library"
DESCRIPTION = "FreeType is a software font engine that is designed to be small, efficient, \
highly customizable, and portable while capable of producing high-quality output (glyph \
images). It can be used in graphics libraries, display servers, font conversion tools, text \
image generation tools, and many other products as well."
HOMEPAGE = "http://www.freetype.org/"
BUGTRACKER = "https://savannah.nongnu.org/bugs/?group=freetype"
SECTION = "libs"

inherit debian-package
require recipes-debian/sources/freetype.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/freetype/freetype"

LICENSE = "FreeType | GPLv2+"
LIC_FILES_CHKSUM = "file://docs/LICENSE.TXT;md5=4af6221506f202774ef74f64932878a1 \
                    file://docs/FTL.TXT;md5=9f37b4e6afa3fef9dba8932b16bd3f97 \
                    file://docs/GPLv2.TXT;md5=8ef380476f642c20ebf40fecb0add2ec"

SRC_URI += "\
           file://use-right-libtool.patch \
          "

inherit autotools pkgconfig multilib_header

# Adapt autotools to work with the minimal autoconf usage in freetype
AUTOTOOLS_SCRIPT_PATH = "${S}/builds/unix"
CONFIGURE_SCRIPT = "${S}/configure"
EXTRA_AUTORECONF += "--exclude=autoheader --exclude=automake"

PACKAGECONFIG ??= "zlib"

PACKAGECONFIG[bzip2] = "--with-bzip2,--without-bzip2,bzip2"
# harfbuzz results in a circular dependency so enabling is non-trivial
PACKAGECONFIG[harfbuzz] = "--with-harfbuzz,--without-harfbuzz,harfbuzz"
PACKAGECONFIG[pixmap] = "--with-png,--without-png,libpng"
PACKAGECONFIG[zlib] = "--with-zlib,--without-zlib,zlib"
PACKAGECONFIG[freetypeconfig] = "--enable-freetype-config=yes,--enable-freetype-config=no,"

EXTRA_OECONF = "CC_BUILD='${BUILD_CC}'"

TARGET_CPPFLAGS += "-D_FILE_OFFSET_BITS=64"

do_install_append() {
	oe_multilib_header freetype2/freetype/config/ftconfig.h
}

BBCLASSEXTEND = "native nativesdk"

# Debian provides 2 sources (freetype and ft2demo), but they will be unpacked separated. Because of it, do_debian_patch fail.
do_debian_unpack_extra_append () {
    if [ -d ${WORKDIR}/ft2demos-${PV} ]; then
        if [ -d ${S}/ft2demos ] ; then
            rm -rf ${S}/ft2demos
        fi
        mv ${WORKDIR}/ft2demos-${PV} ${S}/ft2demos
    fi

    # Fix wrong path in debian patches
    sed -e "s#/ft2docs/#/#g" -i ${S}/debian/patches/{no-web-fonts,hide-donations-information}.patch
}
