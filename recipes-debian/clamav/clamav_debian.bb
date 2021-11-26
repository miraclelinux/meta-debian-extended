# base recipe: meta-security/recipes-security/clamav/clamav_0.99.4.bb 
# base branch: warrior
# base commit: 4f7be0d252f68d8e8d442a7ed8c6e8a852872d28

FILESPATH_append = ":${THISDIR}/files"
SUMMARY = "ClamAV anti-virus utility for Unix - command-line interface"
DESCRIPTION = "ClamAV is an open source antivirus engine for detecting trojans, viruses, malware & other malicious threats."
HOMEPAGE = "http://www.clamav.net/index.html"
SECTION = "security"
LICENSE = "LGPL-2.1"

DEPENDS = "glibc llvm libtool db openssl zlib curl libxml2 bison pcre2 json-c libcheck"
DEPENDS += "systemd libmspack"
 
LIC_FILES_CHKSUM = "file://COPYING;beginline=2;endline=3;md5=f7029fbbc5898b273d5902896f7bbe17"

LEAD_SONAME = "libclamav.so"
SO_VER = "9.0.5"

BINCONFIG = "${bindir}/clamav-config"

inherit cmake chrpath pkgconfig useradd systemd multilib_header multilib_script
inherit debian-package
require recipes-debian/sources/clamav.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/clamav-0.103.3+dfsg"

SRC_URI += "file://clamd.conf \
           file://freshclam.conf \
           file://volatiles.03_clamav \
           file://tmpfiles.clamav \
           file://headers_fixup.patch \
           file://oe_cmake_fixup.patch \
           file://fix_systemd_socket.patch \
           file://0001-cmake-Fix-stuct-packing-alignment-attribute-test.patch \
           file://0002-Workarround-to-fix-error-on-do_install.patch \
           file://0003-Fix-configuration-error-when-ENABLE_EXTERNAL_MSPACK-.patch \
           "

UPSTREAM_CHECK_COMMITS = "1"

CLAMAV_UID ?= "clamav"
CLAMAV_GID ?= "clamav"

MULTILIB_SCRIPTS = "${PN}-dev:${bindir}/clamav-config"

EXTRA_OECMAKE = " -DCMAKE_BUILD_TYPE=Release -DOPTIMIZE=ON \
                  -DCLAMAV_GROUP=${CLAMAV_GID} -DCLAMAV_USER=${CLAMAV_UID} \ 
                  -DENABLE_TESTS=OFF -DBUILD_SHARED_LIBS=ON \
                  -DDISABLE_MPOOL=ON -DENABLE_FRESHCLAM_DNS_FIX=ON \
                  -DCMAKE_SKIP_RPATH=TRUE \
                  -DENABLE_EXTERNAL_MSPACK=ON \
                   "

PACKAGECONFIG ?= "  clamonacc \
                 ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "systemd", "", d)}"

PACKAGECONFIG[milter] = "-DENABLE_MILTER=ON ,-DENABLE_MILTER=OFF, curl, curl"
PACKAGECONFIG[clamonacc] = "-DENABLE_CLAMONACC=ON ,-DENABLE_CLAMONACC=OFF,"
PACKAGECONFIG[unrar] = "-DENABLE_UNRAR=ON ,-DENABLE_UNRAR=OFF,"
PACKAGECONFIG[systemd] = "-DENABLE_SYSTEMD=ON -DSYSTEMD_UNIT_DIR=${systemd_system_unitdir}, -DENABLE_SYSTEMD=OFF, systemd"

export OECMAKE_C_FLAGS += " -I${STAGING_INCDIR} -L ${RECIPE_SYSROOT}${nonarch_libdir} -L${STAGING_LIBDIR} -lpthread " 
export OECMAKE_C_FLAGS += "-lsystemd -lmspack" 

do_install_prepend() {
    install -d ${D}/usr/share/clamav
}

do_install_append () {
    install -d ${D}/${sysconfdir}
    install -d -o ${CLAMAV_UID} -g ${CLAMAV_GID} ${D}/${localstatedir}/lib/clamav
    install -d ${D}${sysconfdir}/clamav ${D}${sysconfdir}/default/volatiles

    install -m 644 ${WORKDIR}/clamd.conf ${D}/${prefix}/${sysconfdir}
    install -m 644 ${WORKDIR}/freshclam.conf ${D}/${prefix}/${sysconfdir}
    install -m 0644 ${WORKDIR}/volatiles.03_clamav  ${D}${sysconfdir}/default/volatiles/03_clamav
    sed -i -e 's#${STAGING_DIR_HOST}##g' ${D}${libdir}/pkgconfig/libclamav.pc
    rm ${D}/${libdir}/libclamav.so
    if [ "${INSTALL_CLAMAV_CVD}" = "1" ]; then
        install -m 666 ${S}/clamav_db/* ${D}/${localstatedir}/lib/clamav/.
    fi

    rm ${D}/${libdir}/libfreshclam.so

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)};then
        install -d ${D}${sysconfdir}/tmpfiles.d
        install -m 0644 ${WORKDIR}/tmpfiles.clamav ${D}${sysconfdir}/tmpfiles.d/clamav.conf
    fi
    oe_multilib_header clamav-types.h
}

pkg_postinst_${PN} () {
    if [ -z "$D" ]; then
        if command -v systemd-tmpfiles >/dev/null; then
            systemd-tmpfiles --create ${sysconfdir}/tmpfiles.d/clamav.conf
        elif [ -e ${sysconfdir}/init.d/populate-volatile.sh ]; then
            ${sysconfdir}/init.d/populate-volatile.sh update
        fi
    fi
}

PACKAGES += "${PN}-daemon ${PN}-clamdscan ${PN}-freshclam ${PN}-libclamav"

FILES_${PN} = "${bindir}/clambc ${bindir}/clamscan ${bindir}/clamsubmit ${sbindir}/clamonacc \
                ${bindir}/*sigtool ${mandir}/man1/clambc* ${mandir}/man1/clamscan* \
                ${mandir}/man1/sigtool* ${mandir}/man1/clambsubmit*  \
                ${docdir}/clamav/*"

FILES_${PN}-clamdscan = " ${bindir}/clamdscan \
                        ${docdir}/clamdscan/* \
                        ${mandir}/man1/clamdscan* \
                        "

FILES_${PN}-daemon = "${bindir}/clamconf ${bindir}/clamdtop ${sbindir}/clamd \
                        ${mandir}/man1/clamconf* ${mandir}/man1/clamdtop* \
                        ${mandir}/man5/clamd*  ${mandir}/man8/clamd* \
                        ${sysconfdir}/clamd.conf* \
                        /usr/etc/clamd.conf* \
                        ${systemd_system_unitdir}/clamav-daemon/* \
                        ${docdir}/clamav-daemon/*  ${sysconfdir}/clamav-daemon \
                        ${sysconfdir}/logcheck/ignore.d.server/clamav-daemon \
                        ${systemd_system_unitdir}/clamav-daemon.service \
                        ${systemd_system_unitdir}/clamav-clamonacc.service \
                        ${systemd_system_unitdir}/clamav-daemon.socket \
                        "

FILES_${PN}-freshclam = "${bindir}/freshclam \
                        ${sysconfdir}/freshclam.conf*  \
                        /usr/etc/freshclam.conf*  \
                        ${sysconfdir}/clamav ${sysconfdir}/default/volatiles \
                        ${sysconfdir}/tmpfiles.d/*.conf \
                        ${localstatedir}/lib/clamav \
                        ${docdir}/${PN}-freshclam ${mandir}/man1/freshclam.* \
                        ${mandir}/man5/freshclam.conf.* \
                        ${systemd_system_unitdir}/clamav-freshclam.service \
                        /usr/share/clamav \
                        "

FILES_${PN}-dev = " ${bindir}/clamav-config ${libdir}/*.la \
                    ${libdir}/pkgconfig/*.pc \
                    ${mandir}/man1/clamav-config.* \
                    ${includedir}/*.h ${docdir}/libclamav* "

FILES_${PN}-staticdev = "${libdir}/*.a"

FILES_${PN}-libclamav = "${libdir}/libclamav.so* ${libdir}/libclammspack.so* \
                         ${libdir}/libfreshclam.so* ${docdir}/libclamav/* \
                         ${libdir}/libmspack* "

FILES_${PN}-doc = "${mandir}/man/* \
                   ${datadir}/man/* \
                   ${docdir}/* "

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system ${CLAMAV_UID}"
USERADD_PARAM_${PN} = "--system -g ${CLAMAV_GID} --home-dir  \
    ${localstatedir}/lib/${BPN} \
    --no-create-home  --shell /sbin/nologin ${BPN}"

RPROVIDES_${PN} += "${PN}-systemd"
RREPLACES_${PN} += "${PN}-systemd"
RCONFLICTS_${PN} += "${PN}-systemd"
SYSTEMD_PACKAGES  = "${PN}-daemon ${PN}-freshclam"
SYSTEMD_SERVICE_${PN}-daemon = "clamav-daemon.service"
SYSTEMD_SERVICE_${PN}-freshclam = "clamav-freshclam.service"

RDEPENDS_${PN} = "openssl ncurses-libncurses libxml2 libbz2 ncurses-libtinfo curl libpcre2 clamav-libclamav"
RRECOMMENDS_${PN} = "clamav-freshclam"
RDEPENDS_${PN}-freshclam = "clamav"
RDEPENDS_${PN}-daemon = "clamav clamav-freshclam"
