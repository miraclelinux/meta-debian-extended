# base recipe: meta/recipes-multimedia/libpng/libpng_1.6.36.bb
# base branch: warrior
# base commit: 4e2c4018e03e8498efce9b826551c2fdb373e16d

SUMMARY = "PNG image format decoding library"
HOMEPAGE = "http://www.libpng.org/"
SECTION = "libs"
LICENSE = "Libpng"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12b4ec50384c800bc568f519671b120c \
                    file://png.h;endline=144;md5=15ae15f53376306868259924a9db4e05 \
                    "
DEPENDS = "zlib"

inherit debian-package
require recipes-debian/sources/libpng1.6.inc
BPN = "libpng"

UPSTREAM_CHECK_URI = "http://libpng.org/pub/png/libpng.html"

BINCONFIG = "${bindir}/libpng-config ${bindir}/libpng16-config"

inherit autotools binconfig-disabled pkgconfig

# Work around missing symbols
EXTRA_OECONF_append_class-target = " ${@bb.utils.contains("TUNE_FEATURES", "neon", "--enable-arm-neon=on", "--enable-arm-neon=off" ,d)}"

PACKAGES =+ "${PN}-tools"

FILES_${PN}-tools = "${bindir}/png-fix-itxt ${bindir}/pngfix ${bindir}/pngcp"

BBCLASSEXTEND = "native nativesdk"
