SUMMARY = "Audio Sample Rate Conversion library"
HOMEPAGE = "http://www.mega-nerd.com/SRC/"
SECTION = "libs"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=336d6faf40fb600bafb0061f4052f1f4 \
                    file://src/samplerate.c;beginline=1;endline=7;md5=5b6982a8c2811c7312c13cccbf55f55e"

inherit debian-package
require recipes-debian/sources/libsamplerate.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/libsamplerate-${PV}"
FILESPATH_append = ":${COREBASE}/meta/recipes-multimedia/libsamplerate/libsamplerate0"

DEPENDS = "libsndfile1"

SRC_URI += " \
           file://0001-configure.ac-improve-alsa-handling.patch \
"

CVE_PRODUCT = "libsamplerate"

UPSTREAM_CHECK_URI = "http://www.mega-nerd.com/SRC/download.html"

inherit autotools pkgconfig

# FFTW and ALSA are only used in tests and examples, so they don't affect
# normal builds. It should be safe to ignore these, but explicitly disabling
# them adds some extra certainty that builds are deterministic.
EXTRA_OECONF = "--disable-fftw --disable-alsa"
