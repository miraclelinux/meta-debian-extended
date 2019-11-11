inherit debian-package
require recipes-debian/sources/aide.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=c93c0550bd3173f4504b2cbd8991e50b"

SRC_URI += "file://aide.conf"

DEPENDS = "acl attr bison-native e2fsprogs libgpg-error libmhash libpcre"

inherit autotools

EXTRA_OECONF = "--disable-static --with-xattr --with-posix-acl --with-e2fsattrs"

do_compile_prepend() {
	sed -i 's:cross:ino64_t:g' config.h
}

do_install_append() {
        install -d ${D}/etc
        install -m 0600 ${WORKDIR}/aide.conf ${D}/etc/.

        install -d ${D}/var/log/aide
        install -d ${D}/var/lib/aide
}
