# base recipe: meta-luv/recipes-devtools/sbsigntool/sbsigntool_git.bb
# base branch: master
# base commit: ffb7d193abfb0c11ed5f0133aca039daddbf9c16
SUMMARY = "Signing utility for UEFI secure boot"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE.GPLv3;md5=9eef91148a9b14ec7f9df333daebc746"

inherit autotools-brokensep pkgconfig
inherit debian-package
require recipes-debian/sources/sbsigntool.inc

SRC_URI += " \
    file://fix-mixed-implicit-and-normal-rules.patch;apply=0 \
    file://disable-man-page-creation.patch \
    "
# https://lists.01.org/hyperkitty/list/luv@lists.01.org/message/RY2WY2COCNZ6QD2HCZI2XBQO5VE7U6QK/
# Patch created based on the above URL
SRC_URI += " \
    file://0001-configure-Fixup-build-dependencies-for-cross-compili.patch \
    "

BBCLASSEXTEND = "native"

DEPENDS = "binutils openssl gnu-efi util-linux"

do_configure_class-native() {
    if [ ! -e lib/ccan.git/Makefile ]; then
        git submodule init
        git submodule update

        patch -p1 -i ${WORKDIR}/fix-mixed-implicit-and-normal-rules.patch
    fi

    OLD_CC="${CC}"

    if [ ! -e lib/ccan ]; then
        export CC="${BUILD_CC}"
        lib/ccan.git/tools/create-ccan-tree \
            --build-type=automake lib/ccan \
                talloc read_write_all build_assert array_size
    fi

    export CC="${OLD_CC}"
    export CFLAGS="-I${STAGING_INCDIR}/efi -I${STAGING_INCDIR}/efi/x86_64"
    ./autogen.sh --noconfigure
    oe_runconf
}

patch() {
    sed -i s#RECIPE_SYSROOT#${RECIPE_SYSROOT}#g ${S}/configure.ac
}

do_patch_append() {
    bb.build.exec_func('patch', d)
}
