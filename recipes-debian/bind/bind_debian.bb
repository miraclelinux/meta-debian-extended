# base recipe: meta/recipes-connectivity/bind/bind_9.11.5-P4.bb
# base branch: warrior
# base commit: 509963a689b31932cdf22f1709fc8dc2728cb074

SUMMARY = "ISC Internet Domain Name Server"
HOMEPAGE = "http://www.isc.org/sw/bind/"
SECTION = "console/network"

LICENSE = "ISC & BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=8f17f64e47e83b60cd920a1e4b54419e"

DEPENDS = "openssl libcap zlib"

inherit debian-package
require recipes-debian/sources/bind9.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/bind-9.11.5-P4"

FILESPATH_append = ":${COREBASE}/meta/recipes-connectivity/bind/bind"
SRC_URI += " \
           file://conf.patch \
           file://named.service \
           file://bind9 \
           file://generate-rndc-key.sh \
           file://make-etc-initd-bind-stop-work.patch \
           file://init.d-add-support-for-read-only-rootfs.patch \
           file://bind-ensure-searching-for-json-headers-searches-sysr.patch \
           file://0001-gen.c-extend-DIRNAMESIZE-from-256-to-512.patch \
           file://0001-lib-dns-gen.c-fix-too-long-error.patch \
           file://0001-configure.in-remove-useless-L-use_openssl-lib.patch \
           file://0001-named-lwresd-V-and-start-log-hide-build-options.patch \
           file://0001-avoid-start-failure-with-bind-user.patch \
"

inherit autotools update-rc.d systemd useradd pkgconfig multilib_script

MULTILIB_SCRIPTS = "${PN}:${bindir}/bind9-config ${PN}:${bindir}/isc-config.sh"

# PACKAGECONFIGs readline and libedit should NOT be set at same time
PACKAGECONFIG ?= "readline"
PACKAGECONFIG[httpstats] = "--with-libxml2=${STAGING_DIR_HOST}${prefix},--without-libxml2,libxml2"
PACKAGECONFIG[readline] = "--with-readline=-lreadline,,readline"
PACKAGECONFIG[libedit] = "--with-readline=-ledit,,libedit"
PACKAGECONFIG[urandom] = "--with-randomdev=/dev/urandom,--with-randomdev=/dev/random,,"
PACKAGECONFIG[python3] = "--with-python=${PYTHON} --with-python-install-dir=${D}/${PYTHON_SITEPACKAGES_DIR} , --without-python, python3-ply-native,"

ENABLE_IPV6 = "--enable-ipv6=${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', 'yes', 'no', d)}"
EXTRA_OECONF = " ${ENABLE_IPV6} --with-libtool --enable-threads \
                 --disable-devpoll --enable-epoll --with-gost=no \
                 --with-gssapi=no --with-ecdsa=yes --with-eddsa=no \
                 --with-lmdb=no \
                 --sysconfdir=${sysconfdir}/bind \
                 --with-openssl=${STAGING_DIR_HOST}${prefix} \
                 --enable-native-pkcs11 \
                 --with-pkcs11=${STAGING_DIR_HOST}${prefix}/lib/softhsm/libsofthsm2.so \
               "

inherit ${@bb.utils.contains('PACKAGECONFIG', 'python3', 'python3native distutils3-base', '', d)}

# dhcp needs .la so keep them
REMOVE_LIBTOOL_LA = "0"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --home ${localstatedir}/cache/bind --no-create-home \
                       --user-group bind"

INITSCRIPT_NAME = "bind"
INITSCRIPT_PARAMS = "defaults"

SYSTEMD_SERVICE_${PN} = "named.service"

sed_native_pkcs11() {
	sed -e 's,libisc\.,libisc-pkcs11.,g' \
		-e 's,libisc-nosymtbl\.,libisc-pkcs11-nosymtbl.,g' \
		-e 's,libdns\.,libdns-pkcs11.,g' \
		-e 's,@CRYPTO@,@CRYPTO_PK11@,g' \
		-e 's,ISC_INCLUDES,ISC_PKCS11_INCLUDES,g' \
		-e 's,DNS_INCLUDES,DNS_PKCS11_INCLUDES,g' \
		-e 's,\(dnssec-[^ ]*\)@EXEEXT@,\1-pkcs11@EXEEXT@,g' \
		-e 's,/lib/isc/,/lib/isc-pkcs11/,g' \
		-e 's,/lib/dns/,/lib/dns-pkcs11/,g' \
		-e 's,named@EXEEXT@,named-pkcs11@EXEEXT@,g' \
		-e 's,@CONTRIB_DLZ@,,g' \
		-e 's,$${DLZDRIVER_INCLUDES} $${DBDRIVER_INCLUDES},,g' \
		-e 's,$${DLZDRIVER_LIBS} $${DBDRIVER_LIBS},,g' \
		-e 's,$${DLZDRIVER_OBJS} $${DBDRIVER_OBJS},,g' \
		-e 's,$${DLZDRIVER_SRCS} $${DBDRIVER_SRCS},,g' \
		-e 's,@DNS_CRYPTO_LIBS@,@DNS_CRYPTO_PK11_LIBS@,g'
}

do_configure_prepend() {
	old=`pwd`
	cd ${S}
	rm -rf bin/named-pkcs11 && cp -r bin/named bin/named-pkcs11
	rm -rf bin/dnssec-pkcs11 && cp -r bin/dnssec bin/dnssec-pkcs11
	rm -rf lib/isc-pkcs11 && cp -r lib/isc lib/isc-pkcs11
	rm -rf lib/dns-pkcs11 && cp -r lib/dns lib/dns-pkcs11
	sed_native_pkcs11 < bin/named/Makefile.in > bin/named-pkcs11/Makefile.in
	sed_native_pkcs11 < bin/dnssec/Makefile.in > bin/dnssec-pkcs11/Makefile.in
	sed_native_pkcs11 < lib/isc/Makefile.in > lib/isc-pkcs11/Makefile.in
	sed_native_pkcs11 < lib/dns/Makefile.in > lib/dns-pkcs11/Makefile.in
	cd $old
}

do_install_prepend() {
	# clean host path in isc-config.sh before the hardlink created
	# by "make install":
	#   bind9-config -> isc-config.sh
	sed -i -e "s,${STAGING_LIBDIR},${libdir}," ${B}/isc-config.sh
}

do_install_append() {

	rmdir "${D}${localstatedir}/run"
	rmdir --ignore-fail-on-non-empty "${D}${localstatedir}"
	install -d -o bind "${D}${localstatedir}/cache/bind"
	install -d "${D}${sysconfdir}/bind"
	install -d "${D}${sysconfdir}/init.d"
	install -m 644 ${S}/conf/* "${D}${sysconfdir}/bind/"
	install -m 755 "${S}/init.d" "${D}${sysconfdir}/init.d/bind"
        if ${@bb.utils.contains('PACKAGECONFIG', 'python3', 'true', 'false', d)}; then
		sed -i -e '1s,#!.*python3,#! /usr/bin/python3,' \
		${D}${sbindir}/dnssec-coverage \
		${D}${sbindir}/dnssec-checkds \
		${D}${sbindir}/dnssec-keymgr
	fi

	# Install systemd related files
	install -d ${D}${sbindir}
	install -m 755 ${WORKDIR}/generate-rndc-key.sh ${D}${sbindir}
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/named.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@BASE_BINDIR@,${base_bindir},g' \
	       -e 's,@SBINDIR@,${sbindir},g' \
	       ${D}${systemd_unitdir}/system/named.service

	install -d ${D}${sysconfdir}/default
	install -m 0644 ${WORKDIR}/bind9 ${D}${sysconfdir}/default

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/tmpfiles.d
		echo "d /run/named 0755 bind bind - -" > ${D}${sysconfdir}/tmpfiles.d/bind.conf
	fi
}

CONFFILES_${PN} = " \
	${sysconfdir}/bind/named.conf \
	${sysconfdir}/bind/named.conf.local \
	${sysconfdir}/bind/named.conf.options \
	${sysconfdir}/bind/db.0 \
	${sysconfdir}/bind/db.127 \
	${sysconfdir}/bind/db.empty \
	${sysconfdir}/bind/db.local \
	${sysconfdir}/bind/db.root \
	"

ALTERNATIVE_${PN}-utils = "nslookup"
ALTERNATIVE_LINK_NAME[nslookup] = "${bindir}/nslookup"
ALTERNATIVE_PRIORITY = "100"

PACKAGE_BEFORE_PN += "${PN}-utils"
FILES_${PN}-utils = "${bindir}/host ${bindir}/dig ${bindir}/mdig ${bindir}/nslookup ${bindir}/nsupdate"
FILES_${PN}-dev += "${bindir}/isc-config.h"
FILES_${PN} += "${sbindir}/generate-rndc-key.sh"

PACKAGE_BEFORE_PN += "${PN}-libs"
FILES_${PN}-libs = "${libdir}/*.so*"
FILES_${PN}-staticdev += "${libdir}/*.la"

PACKAGE_BEFORE_PN += "${@bb.utils.contains('PACKAGECONFIG', 'python3', 'python3-bind', '', d)}"
FILES_python3-bind = "${sbindir}/dnssec-coverage ${sbindir}/dnssec-checkds \
                ${sbindir}/dnssec-keymgr ${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-utils = "bash"
RDEPENDS_${PN}-dev = ""
RDEPENDS_python3-bind = "python3-core python3-ply"
