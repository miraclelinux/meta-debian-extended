# base recipe: meta-openembedded/meta-oe/recipes-crypto/cryptsetup/cryptsetup_2.1.0.bb
# base branch: warrior
# base commit: e9434ec4ce3aa0efe1a802d7fffd2bbd7f8c5a5d

SUMMARY = "Manage plain dm-crypt and LUKS encrypted volumes"
DESCRIPTION = "Cryptsetup is used to conveniently setup dm-crypt managed \
device-mapper mappings. These include plain dm-crypt volumes and \
LUKS volumes. The difference is that LUKS uses a metadata header \
and can hence offer more features than plain dm-crypt. On the other \
hand, the header is visible and vulnerable to damage."
HOMEPAGE = "https://gitlab.com/cryptsetup/cryptsetup"
SECTION = "console"
LICENSE = "GPL-2.0-with-OpenSSL-exception"
LIC_FILES_CHKSUM = "file://COPYING;md5=32107dd283b1dfeb66c9b3e6be312326"

DEPENDS = "util-linux libdevmapper popt libgcrypt json-c"
DEPENDS_append_class-native = " gettext-native"

inherit debian-package
require recipes-debian/sources/cryptsetup.inc

inherit autotools gettext pkgconfig

# Use openssl because libgcrypt drops root privileges
# if libgcrypt is linked with libcap support
PACKAGECONFIG ??= "openssl"
PACKAGECONFIG[openssl] = "--with-crypto_backend=openssl,,openssl"
PACKAGECONFIG[gcrypt] = "--with-crypto_backend=gcrypt,,libgcrypt"

RRECOMMENDS_${PN} = "kernel-module-aes-generic \
                     kernel-module-dm-crypt \
                     kernel-module-md5 \
                     kernel-module-cbc \
                     kernel-module-sha256-generic \
                     kernel-module-xts \
"

EXTRA_OECONF = "--enable-static"

FILES_${PN} += "${@bb.utils.contains('DISTRO_FEATURES','systemd','${exec_prefix}/lib/tmpfiles.d/cryptsetup.conf', '', d)}"

BBCLASSEXTEND = "native nativesdk"

do_configure() {
    cd ${S}
    ./autogen.sh
    cd ${B}
    oe_runconf
}
