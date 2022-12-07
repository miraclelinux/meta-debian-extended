# base recipe: meta-openembedded/meta-oe/recipes-connectivity/krb5/krb5_1.17.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8
SUMMARY = "Runtime library for the Kerberos v5 API for the main part used by the application and the Kerberos client"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://NOTICE;md5=aff541e7261f1926ac6a2a9a7bbab839"

DEPENDS = "bison-native ncurses util-linux e2fsprogs e2fsprogs-native openssl"

inherit autotools-brokensep binconfig debian-package
require recipes-debian/sources/krb5.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/krb5-${PV}"

SRC_URI += "file://0001-aclocal-Add-parameter-to-disable-keyutils-detection.patch;patchdir=src \
           file://crosscompile_nm.patch;patchdir=src"

EXTRA_OECONF += " --without-tcl --with-system-et --disable-rpath"
CACHED_CONFIGUREVARS += "krb5_cv_attr_constructor_destructor=yes ac_cv_func_regcomp=yes \
                  ac_cv_printf_positional=yes ac_cv_file__etc_environment=yes \
                  ac_cv_file__etc_TIMEZONE=no"

CFLAGS_append = " -fPIC -DDESTRUCTOR_ATTR_WORKS=1 -I${STAGING_INCDIR}/et"
CFLAGS_append_riscv64 = " -D_REENTRANT -pthread"
LDFLAGS_append = " -pthread"

# The krb5 source is extracted under src dirctory, so set the confiure file path.
# To apply debian patches, extracted the krb5 source under src dirctory.
CONFIGURE_SCRIPT = "${S}/src/configure"

do_configure() {
    # Change Dirctory where the configure.in exists.
    cd src
    gnu-configize --force
    autoreconf
    oe_runconf
}

do_compile_prepend() {
    # Change Dirctory where the Makefile exists.
    cd src
}

do_install() {
    install -d ${D}${libdir}

    # Install libkrb5-3 files
    install -d ${D}${libdir}/krb5/plugins/preauth
    cp -r --no-preserve=ownership ${S}/src/lib/krb5/libkrb5${SOLIBS} ${D}${libdir}
    install -m 0600 ${S}/src/plugins/preauth/spake/spake.so ${D}${libdir}/krb5/plugins/preauth
    install -d ${D}${docdir}/libkrb5-3
    install -m 0644 ${S}/debian/NEWS ${D}${docdir}/libkrb5-3/NEWS.Debian
    install -m 0644 ${S}/debian/README.Debian ${D}${docdir}/libkrb5-3/
    install -m 0644 ${S}/README ${D}${docdir}/libkrb5-3/
    install -m 0644 ${S}/debian/changelog ${D}${docdir}/libkrb5-3/changelog.Debian
    install -m 0644 ${S}/debian/copyright ${D}${docdir}/libkrb5-3

    # Install libk5crypto3 files
    cp -r --no-preserve=ownership ${S}/src/lib/crypto/libk5crypto${SOLIBS} ${D}${libdir}
    install -d ${D}${docdir}/libkrb5support0
    install -m 0644 ${S}/debian/NEWS ${D}${docdir}/libkrb5support0/NEWS.Debian
    install -m 0644 ${S}/debian/changelog ${D}${docdir}/libkrb5support0/changelog.Debian
    install -m 0644 ${S}/debian/copyright ${D}${docdir}/libkrb5support0

    # Install libkrb5support0 files
    cp -r --no-preserve=ownership ${S}/src/util/support/libkrb5support${SOLIBS} ${D}${libdir}
    install -d ${D}${docdir}/libk5crypto3
    install -m 0644 ${S}/debian/NEWS ${D}${docdir}/libk5crypto3/NEWS.Debian
    install -m 0644 ${S}/debian/changelog ${D}${docdir}/libk5crypto3/changelog.Debian
    install -m 0644 ${S}/debian/copyright ${D}${docdir}/libk5crypto3

    # Install libkrb5-dev files
    install -d ${D}${includedir}/gssapi
    install -m 0644 ${S}/src/include/gssapi/*.h ${D}${includedir}/gssapi
    install -m 0644 ${S}/src/lib/gssapi/mechglue/mechglue.h ${D}${includedir}/gssapi
    rm -f ${D}${includedir}/gssapi/gssapi_alloc.h

    install -d ${D}${includedir}/gssrpc
    install -m 0644 ${S}/src/include/gssrpc/*.h ${D}${includedir}/gssrpc

    install -d ${D}${includedir}/kadm5
    install -m 0644 ${S}/src/include/kadm5/admin.h ${D}${includedir}/kadm5
    install -m 0644 ${S}/src/include/kadm5/chpass_util_strings.h ${D}${includedir}/kadm5
    install -m 0644 ${S}/src/include/kadm5/kadm_err.h ${D}${includedir}/kadm5

    install -d ${D}${includedir}/krb5
    install -m 0644 ${S}/src/include/krb5/*.h ${D}${includedir}/krb5
    rm -f ${D}${includedir}/krb5/audit_plugin.h
    rm -f ${D}${includedir}/krb5/authdata_plugin.h
    rm -f ${D}${includedir}/krb5/kdcauthdata_plugin.h

    install -m 0644 ${S}/src/include/gssapi.h ${D}${includedir}
    install -m 0644 ${S}/src/include/kdb.h ${D}${includedir}
    install -m 0644 ${S}/src/include/krb5.h ${D}${includedir}
    install -m 0644 ${S}/src/include/profile.h ${D}${includedir}

    install -d ${D}${libdir}
    cp -r --no-preserve=ownership ${S}/src/lib/*.so ${D}${libdir}
    ln -sf libkadm5clnt_mit.so ${D}${libdir}/libkadm5clnt.so
    ln -sf libkadm5srv_mit.so ${D}${libdir}/libkadm5srv.so
    rm -f ${D}${libdir}/libkrad.so
    rm -f ${D}${libdir}/libverto.so
}

PACKAGES =+ "libk5crypto3 \
             libk5crypto3-doc \
             libkrb5support0 \
             libkrb5support0-doc"

RDEPENDS_${PN} = "libk5crypto3 libkrb5support0"
RDEPENDS_libk5crypto3 = "libkrb5support0"

FILES_${PN} = "${libdir}/libkrb5${SOLIBS} \
               ${libdir}/krb5/plugins/preauth/spake.so"
FILES_${PN}-dev = "${includedir} ${libdir}/*.so"
FILES_libk5crypto3 = "${libdir}/libk5crypto${SOLIBS}"
FILES_libk5crypto3-doc = "${docdir}/libk5crypto3"
FILES_libkrb5support0 = "${libdir}/libkrb5support${SOLIBS}"
FILES_libkrb5support0-doc = "${docdir}/libkrb5support0"
