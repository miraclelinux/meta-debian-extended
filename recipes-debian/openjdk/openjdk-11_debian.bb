LICENSE  = "GPL-2.0-with-classpath-exception"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3e0b59f8fac05c3c03d4a26bbda13f8f"

inherit debian-package
require recipes-debian/sources/${BPN}.inc
inherit autotools-brokensep pkgconfig

SRC_URI_append = " \
    file://0001-make-autoconf-toolchain-remove-invalid-compiler-chec.patch \
    file://0002-make-lib-Lib-java.smartcardio.gmk-Prevent-host-conta.patch \
"

DEPENDS += " \
    zip-native unzip-native libxslt attr \
    fontconfig freetype libffi \
    giflib libpng zlib jpeg lcms pcsc-lite harfbuzz \
"

PACKAGECONFIG = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11 cups', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'alsa', '', d)} \
"

PACKAGECONFIG[x11] = "--with-x,,libx11 xorgproto libxt libxext libxrender libxtst"
PACKAGECONFIG[cups] = "--with-cups=${STAGING_DIR_HOST}/usr,,cups"
PACKAGECONFIG[alsa] = "--with-alsa,,alsa-lib"

EXTRA_CFLAGS = " \
    -Wdate-time \
    -D_FORTIFY_SOURCE=2 \
    -Wformat \
    -fno-stack-protector \
    -Wno-deprecated-declarations \
    -I${STAGING_INCDIR}/PCSC \
"

EXTRA_LDFLAGS = "-Xlinker -z -Xlinker relro -Xlinker -Bsymbolic-functions"

EXTRA_OECONF_append = " \
    --with-jvm-variants=server \
    --with-boot-jdk=/usr/lib/jvm/java-11-openjdk-amd64 \
    --disable-precompiled-headers \
    --with-jvm-features=zgc,shenandoahgc \
    --disable-ccache \
    --with-debug-level=release \
    --with-native-debug-symbols=zipped \
    --disable-warnings-as-errors \
    --disable-javac-server \
    --with-stdc++lib=dynamic \
    --with-giflib=system \
    --with-libpng=system \
    --with-zlib=system \
    --with-libjpeg=system \
    --with-lcms=system \
    --with-pcsclite=system \
    --with-harfbuzz=system \
    --with-extra-cflags='${CFLAGS} ${EXTRA_CFLAGS}' \
    --with-extra-cxxflags='${CXXFLAGS} ${EXTRA_CFLAGS}' \
    --with-extra-ldflags='${LDFLAGS} ${EXTRA_LDFLAGS}' \
    --with-sysroot=${STAGING_DIR_HOST} \
    --enable-option-checking=yes \
    --with-abi-profile=aarch64 \
    --with-cpu-port=aarch64 \
"

EXTRA_BUILD_ENV = "MAKE_VERBOSE=y QUIETLY= LOG=debug IGNORE_OLD_CONFIG=true LIBFFI_LIBS=-lffi_pic JOBS=${@oe.utils.cpu_count()}"

do_configure () {
    cd ${S}
    rm -rf ${S}/build
    mkdir ${S}/build
    cd ${S}/build
    oe_runconf
}

do_compile () {
    cd ${B}
    ${EXTRA_BUILD_ENV} oe_runmake images
}

PACKAGES = "${PN} ${PN}-dev ${PN}-source ${PN}-demo ${PN}-doc ${PN}-src ${PN}-dbg"

do_install () {
    mkdir -p ${D}${DEBIAN_JDK_BASE_DIR}
    cp -rp ${B}/images/jdk/* ${D}${DEBIAN_JDK_BASE_DIR}
    cp -rp ${B}/images/jmods ${D}${DEBIAN_JDK_BASE_DIR}
    cp -rp ${B}/images/jdk/lib/src.zip ${D}${DEBIAN_JDK_BASE_DIR}
    rm ${D}${DEBIAN_JDK_BASE_DIR}/lib/src.zip
    chown -R root:root ${D}${DEBIAN_JDK_BASE_DIR}
}

FILES_${PN} = " \
    ${DEBIAN_JDK_BASE_DIR}/bin/* \
    ${DEBIAN_JDK_BASE_DIR}/jmods/* \
    ${DEBIAN_JDK_BASE_DIR}/lib/* \
    ${DEBIAN_JDK_BASE_DIR}/conf/* \
    ${DEBIAN_JDK_BASE_DIR}/release \
    ${DEBIAN_JDK_BASE_DIR}/legal/* \
"

FILES_${PN}-demo = " \
    ${DEBIAN_JDK_BASE_DIR}/demo/* \
"

FILES_${PN}-doc = " \
    ${DEBIAN_JDK_BASE_DIR}/man/* \
"

FILES_${PN}-dev = " \
    ${DEBIAN_JDK_BASE_DIR}/include/* \
"

FILES_${PN}-source = " \
    ${DEBIAN_JDK_BASE_DIR}/src.zip \
"

B = "${S}/build"

PARALLEL_MAKE=""
PARALLEL_MAKEINST=""

export DEBIAN_JDK_BASE_DIR="${libdir}/jvm/${PN}"

inherit update-alternatives

ALTERNATIVE_PRIORITY = "100"

ALTERNATIVE_${PN} = "java javac jar"
ALTERNATIVE_LINK_NAME[java] = "${bindir}/java"
ALTERNATIVE_TARGET[java] = "${DEBIAN_JDK_BASE_DIR}/bin/java"

ALTERNATIVE_LINK_NAME[javac] = "${bindir}/javac"
ALTERNATIVE_TARGET[javac] = "${DEBIAN_JDK_BASE_DIR}/bin/javac"

ALTERNATIVE_LINK_NAME[jar] = "${bindir}/jar"
ALTERNATIVE_TARGET[jar] = "${DEBIAN_JDK_BASE_DIR}/bin/jar"
